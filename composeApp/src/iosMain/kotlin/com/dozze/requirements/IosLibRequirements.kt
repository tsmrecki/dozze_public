package com.dozze.requirements

import com.dozze.analytics.FirebaseAnalyticsWrapper
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun librariesModule(factory: LibrariesDependencyFactory): Module = module {
    single<FirebaseAnalyticsWrapper> { factory.provideFirebaseAnalytics() }
}