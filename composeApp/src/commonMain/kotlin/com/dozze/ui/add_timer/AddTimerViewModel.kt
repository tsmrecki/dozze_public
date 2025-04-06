package com.dozze.ui.add_timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dozze.analytics.DozzeAnalytics
import com.dozze.storage.database.dao.TimerDao
import com.dozze.storage.database.entities.TimerEntity
import com.dozze.ui.add_timer.model.AddTimerUI
import com.dozze.ui.components.timer_item.TimerItemData
import com.dozze.ui.timers.mapper.getTimeAgo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.minutes

class AddTimerViewModel(
    private val dozzeAnalytics: DozzeAnalytics,
    private val timerDao: TimerDao
) : ViewModel() {

    private val _addTimer = MutableStateFlow(default)
    val addTimer: StateFlow<AddTimerUI> = _addTimer

    private val _timerPreview = MutableStateFlow<TimerItemData?>(null)
    val timerPreview: StateFlow<TimerItemData?> = _timerPreview

    private val _timerPreviewConfirmedMs = MutableStateFlow<Long?>(Clock.System.now().toEpochMilliseconds())

    init {
        viewModelScope.launch { previewTimer() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun previewTimer() {
        combine(_addTimer, _timerPreviewConfirmedMs) { timer, confirmedMs ->
            TimerItemData(
                id = 0,
                name = timer.name,
                confirmAction = timer.confirmAction,
                successAction = timer.successAction,
                confirmedAgo = getTimeAgo(confirmedMs),
                canConfirm = _timerPreview.value?.canConfirm ?: true
            )
        }.flatMapLatest { previewTimer ->
            channelFlow {
                while (isActive) {
                    val confirmedMs = previewTimer.confirmedAgo?.originalTimestamp
                    send(previewTimer.copy(confirmedAgo = getTimeAgo(confirmedMs)))
                    kotlinx.coroutines.delay(1.0.minutes)
                }
            }
        }.collectLatest { _timerPreview.value = it }
    }

    fun updateName(name: String) {
        _addTimer.value = _addTimer.value.copy(
            name = name.take(MAX_NAME_LENGTH),
            canSave = name.isNotBlank()
        )
    }

    fun updateConfirmAction(confirmAction: String) {
        _addTimer.value = _addTimer.value.copy(confirmAction = confirmAction.take(MAX_CONFIRM_LENGTH))
    }

    fun updateSuccessAction(successAction: String) {
        _addTimer.value = _addTimer.value.copy(successAction = successAction.take(MAX_SUCCESS_LENGTH))
    }

    fun previewConfirm() {
        _timerPreviewConfirmedMs.value = Clock.System.now().toEpochMilliseconds()
    }

    fun save() {
        dozzeAnalytics.logEvent(
            DozzeAnalytics.Event.TimerSaved(
                isCustomized = with(_addTimer) { value.confirmAction != null || value.successAction != null })
        )
        viewModelScope.launch {
            val timer = TimerEntity(
                name = _addTimer.value.name,
                confirmAction = _addTimer.value.confirmAction,
                successAction = _addTimer.value.successAction
            )
            timerDao.insert(timer)
        }
    }

    companion object {
        const val MAX_NAME_LENGTH = 30
        const val MAX_CONFIRM_LENGTH = 16
        const val MAX_SUCCESS_LENGTH = 30

        internal val default = AddTimerUI(
            name = "",
            confirmAction = null,
            successAction = null,
            canSave = false
        )
    }
}