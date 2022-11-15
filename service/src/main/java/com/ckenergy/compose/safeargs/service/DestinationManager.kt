package com.ckenergy.compose.safeargs.service

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import com.google.gson.Gson
import java.lang.reflect.InvocationTargetException
import java.net.URLEncoder
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by chengkai on 2022/11/10.
 */
object DestinationManager {

    private const val CLASS_SUFFIX = "DestinationProvider"

    private val clazzMap = ConcurrentHashMap<Class<*>, Any>()

    /**
     * 默认不缓存实例
     */
    @JvmStatic
    fun <T> getDestinationProvider(clazz: Class<T>, cache: Boolean = false): IDestinationProvider<T> {
        var service = clazzMap[clazz]
        if (service == null) {
            synchronized(this) {
                service = clazzMap[clazz]
                if (service == null) {
                    try {
                        val bindClass = Class.forName(clazz.name + CLASS_SUFFIX)
                        service =  bindClass.newInstance().apply {
                            if (cache)
                                clazzMap[clazz] = this
                        }
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: InstantiationException) {
                        e.printStackTrace()
                    } catch (e: NoSuchMethodException) {
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        if (service == null) {
            throw NullPointerException("class ${clazz.simpleName} need add annotation SafeArgs and module add ksp 'com.ckenergy.compose.safeargs:compiler:version'")
        }
        return service as IDestinationProvider<T>
    }

    inline fun <reified T> getArguments(): MutableList<NamedNavArgument>{
        return getDestinationProvider(T::class.java).getArguments()
    }

    inline fun <reified T> getDestination(t: T): String {
        return getDestinationProvider(T::class.java).getDestination(t)
    }

    inline fun <reified T> getRoute(): String {
        return getDestinationProvider(T::class.java).getRoute()
    }

    inline fun <reified T> NavBackStackEntry.parseArguments(): T? {
        return getDestinationProvider(T::class.java).parseArguments(this)
    }

}

