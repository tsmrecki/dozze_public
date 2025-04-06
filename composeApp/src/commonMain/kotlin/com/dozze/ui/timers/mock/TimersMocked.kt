package com.dozze.ui.timers.mock

import com.dozze.ui.components.timer_item.TimerItemData

object TimersMocked {
    val timers = listOf(
        TimerItemData(
            id = 1,
            name = "Vitamin C",
            confirmedAgo = null,
            confirmAction = "Popio",
            successAction = "Popijeno",
            canConfirm = true
        ),
        TimerItemData(
            id = 2,
            name = "Proteinska čokoladica",
            confirmedAgo = TimerItemData.TimeAgo(0L, 0, 2, 14),
            confirmAction = "Pojeo",
            successAction = "Pojedeno",
            canConfirm = false
        )
    )
}
