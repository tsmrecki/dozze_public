package com.dozze.ui.timers.mapper

import com.dozze.storage.database.entities.TimerEntity
import com.dozze.ui.components.timer_item.TimerItemData
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal fun TimerEntity.toUi(nowMs: Long): TimerItemData =
    TimerItemData(
        id = this.id,
        name = this.name,
        confirmAction = this.confirmAction,
        successAction = this.successAction,
        confirmedAgo = getTimeAgo(this.lastConfirmedMs),
        canConfirm = nowMs - (this.lastConfirmedMs ?: 0) > (this.minConfirmationIntervalMs ?: 0)
    )

internal fun getTimeAgo(timestamp: Long?): TimerItemData.TimeAgo? {
    return timestamp?.let {
        val dateTime = Instant
            .fromEpochMilliseconds((Clock.System.now().toEpochMilliseconds() - it))
            .toLocalDateTime(TimeZone.UTC)
        TimerItemData.TimeAgo(
            originalTimestamp = timestamp,
            days = (dateTime.year - 1970) * dateTime.dayOfYear,
            hours = dateTime.hour,
            minutes = dateTime.minute
        )
    }
}
