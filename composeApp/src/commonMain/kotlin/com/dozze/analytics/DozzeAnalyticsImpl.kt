package com.dozze.analytics

class DozzeAnalyticsImpl(private val firebase: FirebaseAnalyticsWrapper) : DozzeAnalytics {

    override fun logEvent(event: DozzeAnalytics.Event) {
        when (event) {
            is DozzeAnalytics.Event.TimerSaved -> firebase.logEvent(
                "timer_saved",
                mapOf("customized" to event.isCustomized)
            )
            is DozzeAnalytics.Event.Tap -> firebase.logEvent("tap", mapOf("item" to event.item))
            is DozzeAnalytics.Event.ScreenView -> firebase.logEvent("screen_view", mapOf("screen_name" to event.name))
        }
    }

}