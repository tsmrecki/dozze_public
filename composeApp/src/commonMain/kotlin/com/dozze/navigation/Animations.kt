package com.dozze.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

private const val ANIM_DURATION = 200
private const val ANIM_HORIZONTAL_OFFSET_PERCENT = 0.4f
private const val ANIM_HORIZONTAL_OFFSET_EXIT_PERCENT = 0.2f
private val easing = FastOutSlowInEasing

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransitionDefault(): EnterTransition {
    return slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(durationMillis = ANIM_DURATION, easing = easing),
        initialOffset = { (it * ANIM_HORIZONTAL_OFFSET_PERCENT).toInt() }
    ) + fadeIn(
        tween(
            durationMillis = ANIM_DURATION / 2,
            easing = easing
        )
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransitionDefault(): ExitTransition {
    return slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(durationMillis = ANIM_DURATION, easing = easing),
        targetOffset = { (it * ANIM_HORIZONTAL_OFFSET_EXIT_PERCENT).toInt() }
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransitionDefault(): EnterTransition {
    return slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(durationMillis = ANIM_DURATION, easing = easing),
        initialOffset = { (it * ANIM_HORIZONTAL_OFFSET_EXIT_PERCENT).toInt() }
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransitionDefault(): ExitTransition {
    return slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(durationMillis = ANIM_DURATION, easing = easing),
        targetOffset = { (it * ANIM_HORIZONTAL_OFFSET_PERCENT).toInt() }
    ) + fadeOut(
        tween(durationMillis = ANIM_DURATION, easing = easing)
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransitionModal(): EnterTransition {
    return slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up)
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransitionModal(): ExitTransition {
    return slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, targetOffset = { it / 4 })
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransitionModal(): EnterTransition {
    return slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up)
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransitionModal(): ExitTransition {
    return slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down)
}
