package com.dozze.requirements

import com.dozze.analytics.AndroidFirebaseAnalyticsImpl
import com.dozze.analytics.FirebaseAnalyticsWrapper
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun librariesModule(factory: LibrariesDependencyFactory): Module = module {
    single<FirebaseAnalyticsWrapper> { AndroidFirebaseAnalyticsImpl() }
}

internal class AndroidLibrariesDependencyFactory : LibrariesDependencyFactory {
    override fun provideFirebaseAnalytics(): FirebaseAnalyticsWrapper {
        return AndroidFirebaseAnalyticsImpl()
    }

}