package com.ckenergy.compose.safeargs.service

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import java.net.URLEncoder

private val gson = Gson()
private const val argName = "SafeArgs"
/**
 * Created by chengkai on 2022/11/10.
 */
class DestinationProviderImpl<T>(private val route: String, private val clazz: Class<T>) :IDestinationProvider<T> {

    override fun getArguments(): MutableList<NamedNavArgument> {
        return arrayListOf(
            navArgument(argName) {
                type = NavType.StringType
                nullable = true
            })
    }

    override fun getDestination(source: T): String {
        return "${route}/${URLEncoder.encode(gson.toJson(source))}"
    }

    override fun getRoute(): String {
        return "${route}/{${argName}}"
    }

    override fun parseArguments(backStackEntry: NavBackStackEntry): T? {
        val data = backStackEntry.arguments?.getString(argName)
        return gson.fromJson(data, clazz)
    }

}