package com.ckenergy.compose.safeargs.service

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.net.URLEncoder

private val gson = Gson()
private const val argName = "SafeArgs"

/**
 * Created by chengkai on 2022/11/14.
 */
class SafeArgsSource(
    val route1: String = "",
) : HashMap<String, Any>() {

    fun addParam(key: String, value: Any): SafeArgsSource {
        this[key] = value
        return this
    }

    fun destination(): String {
        return "${route1}/${URLEncoder.encode(gson.toJson(this))}"
    }

    fun getRoute(): String {
        return "${route1}/{${argName}}"
    }

    companion object {

        fun getRoute(route1: String): String {
            return "${route1}/{${argName}}"
        }

        fun getArguments(): List<NamedNavArgument> {
            return arrayListOf(
                navArgument(argName) {
                    type = NavType.StringType
                    nullable = true
                })

        }
    }
}

class SafeArgsParser(backStackEntry: NavBackStackEntry) {

    var jsonObject: JSONObject? = null

    init {
        try {
            jsonObject = JSONObject(backStackEntry.arguments?.getString(argName))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inline fun <reified T> getParam(key: String): T? {
        val any: Any? = when (T::class.java) {
            Int::class.java, Integer::class.java -> jsonObject?.optInt(key)
            Boolean::class.java -> jsonObject?.optBoolean(key)
            String::class.java -> jsonObject?.optString(key)
            Double::class.java -> jsonObject?.optDouble(key)
            Long::class.java -> jsonObject?.optLong(key)
            else -> {
                val data = jsonObject?.opt(key)
                Gson().fromJson(data.toString(), object : TypeToken<T>() {}.type)
            }
        }
        return any as? T
    }

}