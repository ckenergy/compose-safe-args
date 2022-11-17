package com.example.compose.safeargs

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.ckenergy.compose.moduleb.OtherModuleSampleData
import com.ckenergy.compose.safeargs.service.navigateInResumed
import com.ckenergy.compose.safeargs.service.navigateWithSafeArgs
import com.example.compose.safeargs.destination.MainData

class MainActions(val navController: NavHostController) {

    val upPress: (NavBackStackEntry) -> Unit = { from ->
        from.navigateUp()
    }

    val toSecond: (NavBackStackEntry, OtherModuleSampleData) -> Unit = { from, bean->
        navController.navigateWithSafeArgs(from, bean)
    }

    val toFour: (NavBackStackEntry, MainData) -> Unit = { from, bean->
        navController.navigateWithSafeArgs(from, bean)
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

