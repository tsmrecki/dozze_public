package com.dozze.ui.timers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dozze.analytics.DozzeAnalytics
import com.dozze.storage.database.dao.TimerDao
import com.dozze.storage.database.entities.TimerEntity
import com.dozze.storage.datastore.Datastore
import com.dozze.ui.components.timer_item.TimerItemData
import com.dozze.ui.timers.mapper.toUi
import dozze.composeapp.generated.resources.Res
import dozze.composeapp.generated.resources.prefill_confirm
import dozze.composeapp.generated.resources.prefill_onboarded
import dozze.composeapp.generated.resources.prefill_success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.getString
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TimersListViewModel(
    private val datastore: Datastore,
    private val dozzeAnalytics: DozzeAnalytics,
    private val timerDao: TimerDao
) : ViewModel() {

    private val _timersList: MutableStateFlow<List<TimerItemData>> = MutableStateFlow(emptyList())
    val timersList: StateFlow<List<TimerItemData>> = _timersList

    init {
        viewModelScope.launch { prefillAtFirstLaunch() }
        viewModelScope.launch { observeTimers() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun observeTimers() {
        timerDao.getTimers()
            .flowOn(Dispatchers.IO)
            .flatMapLatest { timers -> refreshTimers(timers, 1.0.minutes) }
            .flowOn(Dispatchers.Default)
            .collect { timers -> _timersList.value = timers }
    }

    private fun refreshTimers(timers: List<TimerEntity>, interval: Duration): Flow<List<TimerItemData>> = channelFlow {
        while (isActive) {
            val nowMs = Clock.System.now().toEpochMilliseconds()
            send(timers.map { it.toUi(nowMs) })
            delay(interval)
        }
    }

    private suspend fun prefillAtFirstLaunch() {
        val isPrefilled = datastore.read(Datastore.IS_PREFILLED_AT_LAUNCH).first()
        if ((isPrefilled == null || isPrefilled == false) && _timersList.value.isEmpty()) {
            timerDao.insert(
                TimerEntity(
                    name = getString(Res.string.prefill_onboarded),
                    confirmAction = getString(Res.string.prefill_confirm),
                    successAction = getString(Res.string.prefill_success)
                )
            )
        }
        datastore.write(Datastore.IS_PREFILLED_AT_LAUNCH, true)
    }

    fun confirmTimer(id: Long) {
        dozzeAnalytics.logEvent(DozzeAnalytics.Event.Tap("confirm_timer"))
        val now = Clock.System.now().toEpochMilliseconds()

        viewModelScope.launch {
            timerDao.update(id = id, currentMs = now)
        }
    }

    fun deleteTimer(id: Long) {
        dozzeAnalytics.logEvent(DozzeAnalytics.Event.Tap("delete_timer"))
        viewModelScope.launch {
            timerDao.delete(id)
        }
    }

    fun resetTimers() {
        dozzeAnalytics.logEvent(DozzeAnalytics.Event.Tap("reset_timers"))
        viewModelScope.launch {
            _timersList.value.forEach {
                timerDao.update(id = it.id, currentMs = null)
            }
        }
    }

}