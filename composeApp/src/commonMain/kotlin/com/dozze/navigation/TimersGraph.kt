package com.dozze.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dozze.ui.add_timer.AddTimerScreen
import com.dozze.ui.timers.TimersListScreen

private val startDestination = Timers

@Composable
fun TimersGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.fillMaxSize()
    ) {
        dozzeComposable(destination = Timers) {
            TimersListScreen(
                onAddTimer = { navController.navigateTo(AddTimer) })
        }

        dozzeComposable(destination = AddTimer) {
            AddTimerScreen(
                onSaved = { navController.navigateUp() },
                onBack = { navController.navigateUp() })
        }
    }
}