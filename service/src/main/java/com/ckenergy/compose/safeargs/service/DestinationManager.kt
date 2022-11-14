package com.ckenergy.compose.safeargs.service

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by chengkai on 2022/11/10.
 */
object DestinationManager {

    const val CLASS_SUFFIX = "DestinationProvider"

    private val clazzMap = ConcurrentHashMap<Class<*>, Any>()

    @JvmStatic
    fun <T> getDestinationProvider(clazz: Class<T>): IDestinationProvider<T> {
        var service = clazzMap[clazz]
        if (service == null) {
            synchronized(this) {
                service = clazzMap[clazz]
                if (service == null) {
                    try {
                        val bindClass = Class.forName(clazz.name + CLASS_SUFFIX)
                        service =  bindClass.newInstance().apply {
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

