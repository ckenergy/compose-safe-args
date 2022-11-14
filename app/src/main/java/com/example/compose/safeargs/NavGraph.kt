package com.example.compose.safeargs

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.*
import com.ckenergy.compose.safeargs.service.DestinationManager
import com.ckenergy.compose.safeargs.service.DestinationManager.parseArguments
import com.example.compose.safeargs.destination.SampleData
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    finishActivity: () -> Unit = {},
    startDestination: String = MainDestinations.ROUTE_MAIN
) {
    val navController = rememberAnimatedNavController()
    val actions = remember(navController) { MainActions(navController) }

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composableHorizontal(MainDestinations.ROUTE_MAIN) {
            MainPage({
                val bean = SampleData(1, "SafeArgs", listOf("1", "2", "3"))
                actions.toSecondFilter(it, bean)
            }, {
                finishActivity()
            })
        }

        composable<SampleData> { entry, it ->
            if (it != null)
                SecondPage(
                    it
                ) {
                    actions.upPress(entry)
                }
        }

    }
}

@ExperimentalAnimationApi
inline fun <reified T> NavGraphBuilder.composable(crossinline content: @Composable (NavBackStackEntry, T?) -> Unit) {
    val provider = DestinationManager.getDestinationProvider(T::class.java)
    composableHorizontal(
        route = provider.getRoute(),
        arguments = provider.getArguments()
    ) {
        val deviceFilterBean: T? = it.parseArguments()
        content(it, deviceFilterBean)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composableHorizontal(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    val springSpec = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold
    )

    this@composableHorizontal.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = springSpec)
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = springSpec
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = springSpec
            )

        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = springSpec
            )

        },
        content = content,
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composableVertical(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    this@composableVertical.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            // Let's make for a really long fade in
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(500)
            )
        },
        content = content,
    )
}