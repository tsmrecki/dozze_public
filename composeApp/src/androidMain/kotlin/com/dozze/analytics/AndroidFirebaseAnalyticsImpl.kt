package com.dozze.analytics

import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics

internal class AndroidFirebaseAnalyticsImpl : FirebaseAnalyticsWrapper {
    private val firebase = Firebase.analytics

    override fun logEvent(name: String, params: Map<String, Any>?) {
        val bundle = Bundle().apply {
            for (entry in params ?: emptyMap()) {
                when (val value = entry.value) {
                    is Int -> putInt(entry.key, value)
                    is String -> putString(entry.key, value)
                    is Double -> putDouble(entry.key, value)
                    is Float -> putFloat(entry.key, value)
                    is Boolean -> putBoolean(entry.key, value)
                    else -> throw IllegalArgumentException("Unsupported parameter type: ${value.javaClass.name}")
                }
            }
        }
        firebase.logEvent(name, bundle)
    }

    override fun setAnalyticsEnabled(enabled: Boolean) {
        firebase.setAnalyticsCollectionEnabled(enabled)
    }

}