package com.dozze.analytics

interface FirebaseAnalyticsWrapper {
    fun logEvent(name: String, params: Map<String, Any>? = emptyMap())
    fun setAnalyticsEnabled(enabled: Boolean)
}