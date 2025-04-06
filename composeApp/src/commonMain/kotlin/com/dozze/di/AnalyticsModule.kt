package com.dozze.di

import com.dozze.analytics.DozzeAnalytics
import com.dozze.analytics.DozzeAnalyticsImpl
import org.koin.dsl.module

internal val analyticsModule = module {
    single<DozzeAnalytics> { DozzeAnalyticsImpl(firebase = get()) }
}