package com.example.compose.safeargs

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.ckenergy.compose.safeargs.service.DestinationManager
import com.example.compose.safeargs.destination.SampleData

class MainActions(val navController: NavHostController) {

    val upPress: (NavBackStackEntry) -> Unit = { from ->
        from.navigateUp()
    }

    val toSecondFilter: (NavBackStackEntry, SampleData) -> Unit = { from, bean->
        from.navigate(DestinationManager.getDestination(bean))

    }

    private fun NavBackStackEntry.navigate(route: String) {
        navigate {
            navController.navigate(route)
        }
    }

    private fun NavBackStackEntry.navigateUp() {
        navigate {
            navController.navigateUp()
        }
    }

    private fun NavBackStackEntry.navigate(navigate: () -> Unit) {
        if (lifecycleIsResumed()) {
            navigate()
        }
    }

}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED