package com.dozze.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.koinInject

/**
 * Use for local access of the Analytics interface within composable functions.
 * Useful for various click listeners which might not be represented in the ViewModels.
 *
 * @return analytics interface
 */
@Composable
fun rememberAnalytics(): DozzeAnalytics {
    val analytics: DozzeAnalytics = koinInject<DozzeAnalytics>()
    return remember {
        analytics
    }
}
