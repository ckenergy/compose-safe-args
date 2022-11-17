package com.ckenergy.compose.safeargs.service

import androidx.lifecycle.Lifecycle
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

inline fun <reified T> NavHostController.navigateWithSafeArgs(from: NavBackStackEntry, t: T) {
    from.navigateInResumed {
        navigate(DestinationManager.getDestination(t))
    }
}

fun NavBackStackEntry.parseSafeArgs(): SafeArgsParser {
    return SafeArgsParser(this)
}

fun NavBackStackEntry.navigateInResumed(navigate: () -> Unit) {
    if (lifecycleIsResumed()) {
        navigate()
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED