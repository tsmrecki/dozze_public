package com.dozze.analytics

interface DozzeAnalytics {

    fun logEvent(event: Event)

    sealed class Event {
        data class Tap(val item: String) : Event()
        data class TimerSaved(val isCustomized: Boolean) : Event()
        data class ScreenView(val name: String, val className: String? = null) : Event()
    }
}