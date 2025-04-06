package com.dozze.ui.components.timer_item

data class TimerItemData(
    val id: Long,
    val name: String,
    val confirmAction: String?,
    val successAction: String?,
    val confirmedAgo: TimeAgo?,
    val canConfirm: Boolean
) {
    data class TimeAgo(
        val originalTimestamp: Long,
        val days: Int,
        val hours: Int,
        val minutes: Int
    )
}


