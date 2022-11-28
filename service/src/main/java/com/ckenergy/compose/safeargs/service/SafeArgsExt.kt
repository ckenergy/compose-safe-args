package com.ckenergy.compose.safeargs.service

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

/**
 * Created by chengkai on 2022/11/16.
 */
fun NavHostController.navigateWithSafeArgsInResumed(route: String, builder: SafeArgsSource.() -> Unit) {
    val source = SafeArgsSource(route)
    builder.invoke(source)
    navigate(source.destination())
}

inline fun <reified T> NavHostController.navigateWithSafeArgsInResumed(from: NavBackStackEntry, t: T) {
    from.navigateInResumed {
        navigate(DestinationManager.getDestination(t))
    }
}

inline fun <reified T> NavHostController.navigateWithSafeArgs(t: T) {
    navigate(DestinationManager.getDestination(t))
}

fun NavBackStackEntry.parseSafeArgs(): SafeArgsParser {
    return SafeArgsParser(this)
}

fun NavBackStackEntry.navigateInResumed(navigate: () -> Unit) {
    if (lifecycleIsResumed()) {
        navigate()
    }
}

inline fun <reified T> NavGraphBuilder.composableSafeArgs(crossinline content: @Composable (NavBackStackEntry, T?) -> Unit) {
    val provider = destinationProvider<T>()
    composable(
        route = provider.getRoute(),
        arguments = provider.getArguments()
    ) {
        val data: T? = it.parseArguments()
        content(it, data)
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED