package com.dozze.requirements

import com.dozze.analytics.FirebaseAnalyticsWrapper
import org.koin.core.module.Module

internal expect fun librariesModule(factory: LibrariesDependencyFactory): Module

interface LibrariesDependencyFactory {
    fun provideFirebaseAnalytics(): FirebaseAnalyticsWrapper
}
