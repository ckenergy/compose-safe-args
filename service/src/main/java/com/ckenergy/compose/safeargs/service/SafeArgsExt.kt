package com.ckenergy.compose.safeargs.service

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

/**
 * Created by chengkai on 2022/11/16.
 */
fun NavHostController.navigateWithSafeArgs(route: String, builder: SafeArgsSource.() -> Unit) {
    val source = SafeArgsSource(route)
    builder.invoke(source)
    navigate(source.destination())
}

fun NavBackStackEntry.parseSafeArgs(): SafeArgsParser {
    return SafeArgsParser(this)
}