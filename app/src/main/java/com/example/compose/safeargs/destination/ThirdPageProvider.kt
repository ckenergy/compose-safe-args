package com.example.compose.safeargs.destination

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.ckenergy.compose.moduleb.ThirdPage
import com.ckenergy.compose.safeargs.service.SafeArgsSource
import com.ckenergy.compose.safeargs.service.parseSafeArgs
import com.example.compose.safeargs.MainDestinations
import com.example.compose.safeargs.composableHorizontal

/**
 * Created by chengkai on 2022/11/16.
 */
class ThirdPageProvider(var index: Int, var list: List<String>, var onBack: () -> Unit)

class Provider<T1, T2, T3>(val t1: T1? = null, val t2: T2? = null, val t3: T3?= null)

@ExperimentalAnimationApi
fun NavGraphBuilder.thirdPageComposable(builder: ThirdPageProvider.(NavBackStackEntry) -> Unit) {
    composableHorizontal(
        SafeArgsSource.getRoute(MainDestinations.ROUTE_THIRD),
        arguments = SafeArgsSource.getArguments()
    ) {
        val parser = it.parseSafeArgs()
        val list = parser.getParam<List<String>>("list") ?: listOf()
        val index = parser.getParam<Int>("index") ?: 0
        val thirdPageProvider= ThirdPageProvider(index, list, {})
        Log.d("thirdPageComposable", "class:${(thirdPageProvider.onBack)::class.java}")
        builder.invoke(thirdPageProvider, it)
        ThirdPage(thirdPageProvider.index, thirdPageProvider.list, thirdPageProvider.onBack)
    }
}
@ExperimentalAnimationApi
fun NavGraphBuilder.composable(action: (Int, List<String>, Function<*>) -> Unit, index: Int?, list: List<String>?, onBack: () -> Unit) {
    composableHorizontal(
        SafeArgsSource.getRoute(MainDestinations.ROUTE_THIRD),
        arguments = SafeArgsSource.getArguments()
    ) {
        val parser = it.parseSafeArgs()
        val list1 = parser.getParam<List<String>>("list") ?: list ?: listOf()
        val index1 = parser.getParam<Int>("index") ?: index ?: 0
        Log.d("thirdPageComposable", "class:${(onBack)::class.java}")
        action(index1, list1, onBack)
    }
}

@ExperimentalAnimationApi
fun <T1, T2, T3> NavGraphBuilder.composable1(action: @Composable ((T1, T2, T3) -> Unit), values: (NavBackStackEntry) -> Provider<T1, T2, T3>) {
    composableHorizontal(
        SafeArgsSource.getRoute(MainDestinations.ROUTE_THIRD),
        arguments = SafeArgsSource.getArguments()
    ) {
        val parser = it.parseSafeArgs()
        val a = values.invoke(it)
        action((parser.getParam<Int>("index") as T1), parser.getParam<List<String>>("list") as T2, a.t3!!)
    }
}

class  A<T1, T2, T3> {
    val a: ((T1?, T2?, T3?) -> Unit)? = null

}