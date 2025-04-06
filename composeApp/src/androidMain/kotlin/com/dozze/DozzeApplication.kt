package com.dozze

import android.app.Application
import com.dozze.di.initKoin
import com.dozze.requirements.AndroidLibrariesDependencyFactory
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.crashlytics.crashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class DozzeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.analytics.setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
        Firebase.crashlytics.isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG

        initKoin(libraryFactory = AndroidLibrariesDependencyFactory()) {
            androidLogger(level = Level.DEBUG)
            androidContext(this@DozzeApplication)
        }
    }
}
