package com.example.compose.safeargs

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.ckenergy.compose.moduleb.OtherModuleSampleData
import com.ckenergy.compose.safeargs.service.*
import com.example.compose.safeargs.destination.MainData
import com.google.gson.GsonBuilder

class MainActions(val navController: NavHostController) {

    val upPress: (NavBackStackEntry) -> Unit = { from ->
        from.navigateUp()
    }

    val toSecond: (NavBackStackEntry, OtherModuleSampleData) -> Unit = { from, bean->
        navController.navigateWithSafeArgsInResumed(from, bean)
    }

    val toFour: (NavBackStackEntry, MainData) -> Unit = { _, bean ->
        val destinationProvider = DestinationManager.getDestinationProvider(MainData::class.java)
        destinationProvider.gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        navController.navigate(destinationProvider.getDestination(bean))
    }

    private fun NavBackStackEntry.navigate(route: String) {
        navigateInResumed {
            navController.navigate(route)
        }
    }

    private fun NavBackStackEntry.navigateUp() {
        navigateInResumed {
            navController.navigateUp()
        }
    }

}

