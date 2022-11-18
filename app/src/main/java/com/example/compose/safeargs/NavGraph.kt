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
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.ckenergy.compose.moduleb.OtherModuleSampleData
import com.ckenergy.compose.modulea.SampleData
import com.ckenergy.compose.modulea.SecondPage
import com.ckenergy.compose.moduleb.ThirdPage
import com.ckenergy.compose.safeargs.service.*
import com.example.compose.safeargs.destination.MainData
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
            MainPage(next = {
                //don't use SafeArgs
                //不使用注解传递参数，略麻烦
                navController.navigateWithSafeArgs(MainDestinations.ROUTE_THIRD) {
                    addParam("index", 1)
                    addParam("list", listOf("1", "2", "3"))
                }
            }, four = {
              actions.toFour(it, MainData(1,"four"))
            }, {
                finishActivity()
            })
        }

        //use Gson format data， so SampleData can transform to OtherModuleSampleData when their field name and type are same
        //因为使用的是Gson来序列化，所以SampleData和OtherModuleSampleData名字和类型对应上就能获取到数据,模块间通信使用，
        //当然也可以把SampleData下沉到基础模块
        composableSafeArgs<SampleData> { entry, it ->
            if (it != null)
                SecondPage(it)
        }

        //don't use SafeArgs
        //不使用注解传递参数，略麻烦
        composableSafeArgs(MainDestinations.ROUTE_THIRD) { entry, it ->
            val list: List<String> = it.getParam("list") ?: listOf()
            ThirdPage(it.getParam<Int>("index") ?: 0, list) {
                val data = OtherModuleSampleData(
                    listOf("1", "2", "3"),
                    "OtherModule",
                    1
                )
                actions.toSecond(entry, data)
            }
        }

        composableSafeArgs<MainData> { entry, it ->
            if (it != null)
                FourPage(it) {
                    actions.upPress(entry)
                }
        }

    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composableSafeArgs(route: String, content: @Composable (NavBackStackEntry, SafeArgsParser) -> Unit) {
    composableHorizontal(
        SafeArgsSource.getRoute(route),
        arguments = SafeArgsSource.getArguments()
    ) {
        val parser = it.parseSafeArgs()
        content(it, parser)
    }
}

@ExperimentalAnimationApi
inline fun <reified T> NavGraphBuilder.composableSafeArgs(crossinline content: @Composable (NavBackStackEntry, T?) -> Unit) {
    val provider = destinationProvider<T>()
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

    composable(
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