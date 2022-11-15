package com.ckenergy.compose.safeargs.service

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import java.net.URLEncoder

/**
 * Created by chengkai on 2022/11/10.
 */
interface IDestinationProvider<T> {

    fun getArguments(): MutableList<NamedNavArgument>

    fun getDestination(source: T): String

    fun getDestination(): String {
        return ""
    }

    fun getRoute(): String

    fun parseArguments(backStackEntry: NavBackStackEntry): T?

}