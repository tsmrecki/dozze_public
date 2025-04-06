package com.dozze.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dozze.analytics.DozzeAnalytics
import com.dozze.analytics.rememberAnalytics

open class DozzeDestination(val route: String, internal val name: String)

object Timers : DozzeDestination(route = "timers", name = "Timers List")
object AddTimer : DozzeDestination(route = "add_timer", name = "Add Timer")

fun NavHostController.navigateTo(
    destination: DozzeDestination,
    navOptions: NavOptions? = null
) {
    val routeWithParams = destination.route
    navigate(route = routeWithParams, navOptions = navOptions)
}

fun NavGraphBuilder.dozzeComposable(
    destination: DozzeDestination,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = { enterTransitionDefault() },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = { exitTransitionDefault() },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = { popEnterTransitionDefault() },
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = { popExitTransitionDefault() },
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        val analytics = rememberAnalytics()
        LaunchedEffect(key1 = "analytics", block = {
            analytics.logEvent(
                DozzeAnalytics.Event.ScreenView(
                    name = destination.name,
                    className = destination::class.simpleName
                )
            )
        })
        content(this, it)
    }
}