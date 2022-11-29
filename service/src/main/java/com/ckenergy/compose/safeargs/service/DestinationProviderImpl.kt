package com.ckenergy.compose.safeargs.service

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.net.URLDecoder
import java.net.URLEncoder

private val S_GSON = Gson()

private const val argName = "SafeArgs"

/**
 * Created by chengkai on 2022/11/10.
 */
class DestinationProviderImpl<T>(private val route1: String, private val clazz: Class<T>) :
    IDestinationProvider<T> {

    override fun getArguments(): MutableList<NamedNavArgument> {
        return arrayListOf(
            navArgument(argName) {
                type = NavType.StringType
                nullable = true
            })
    }

    override fun getDestination(source: T): String {
        return "${route1}/${URLEncoder.encode(provideGson().toJson(source))}"
    }

    override fun getRoute(): String {
        return "${route1}/{${argName}}"
    }

    override fun parseArguments(backStackEntry: NavBackStackEntry): T? {
        val data = backStackEntry.arguments?.getString(argName)
        return provideGson().fromJson(URLDecoder.decode(data), clazz)
    }

    private fun provideGson(): Gson = gson?: S_GSON

    override var gson: Gson? = S_GSON
}