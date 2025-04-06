package com.dozze.ui.add_timer.model

data class AddTimerUI(
    val name: String,
    val confirmAction: String?,
    val successAction: String?,
    val canSave: Boolean
)
