package com.dozze.ui.timers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dozze.analytics.DozzeAnalytics
import com.dozze.analytics.rememberAnalytics
import com.dozze.ui.components.draggable_item.DraggableItem
import com.dozze.ui.components.timer_item.TimerItem
import com.dozze.ui.components.timer_item.TimerItemData
import com.dozze.ui.timers.mock.TimersMocked
import dozze.composeapp.generated.resources.Res
import dozze.composeapp.generated.resources.add_timer
import dozze.composeapp.generated.resources.all_timers_will_be_reset
import dozze.composeapp.generated.resources.are_you_sure
import dozze.composeapp.generated.resources.delete
import dozze.composeapp.generated.resources.delete_it
import dozze.composeapp.generated.resources.oops_no
import dozze.composeapp.generated.resources.reset_all
import dozze.composeapp.generated.resources.reset_them
import dozze.composeapp.generated.resources.s_will_be_deleted
import dozze.composeapp.generated.resources.when_did_i_do_something
import io.github.vinceglb.confettikit.compose.ConfettiKit
import io.github.vinceglb.confettikit.core.Angle
import io.github.vinceglb.confettikit.core.Party
import io.github.vinceglb.confettikit.core.Position
import io.github.vinceglb.confettikit.core.emitter.Emitter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration.Companion.seconds


@Composable
fun TimersListScreen(
    timersListViewModel: TimersListViewModel = koinViewModel(),
    onAddTimer: () -> Unit
) {
    val analytics = rememberAnalytics()
    val timersListState = timersListViewModel.timersList.collectAsStateWithLifecycle()
    var showResetDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf<TimerItemData?>(null) }

    LaunchedEffect("app_start") {
        analytics.logEvent(DozzeAnalytics.Event.ScreenView("Timers List", null))
    }

    TimersListContent(
        timersListState = timersListState,
        onConfirmTimer = { timersListViewModel.confirmTimer(it) },
        onResetTimers = { showResetDialog = true },
        onAddTimer = onAddTimer,
        onDeleteTimer = { showDeleteConfirmationDialog = it }
    )

    if (showResetDialog) {
        AlertDialog(
            title = { Text(text = stringResource(Res.string.are_you_sure)) },
            text = { Text(text = stringResource(Res.string.all_timers_will_be_reset)) },
            confirmButton = {
                TextButton(onClick = {
                    showResetDialog = false
                    timersListViewModel.resetTimers()
                }) {
                    Text(text = stringResource(Res.string.reset_them))
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(text = stringResource(Res.string.oops_no))
                }
            },
            onDismissRequest = { showResetDialog = false })
    }
    showDeleteConfirmationDialog?.let { deleteTimer ->
        AlertDialog(
            title = { Text(text = stringResource(Res.string.are_you_sure)) },
            text = { Text(text = stringResource(Res.string.s_will_be_deleted, deleteTimer.name)) },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirmationDialog = null
                    timersListViewModel.deleteTimer(deleteTimer.id)
                }) {
                    Text(text = stringResource(Res.string.delete_it))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmationDialog = null }) {
                    Text(text = stringResource(Res.string.oops_no))
                }
            },
            onDismissRequest = { showDeleteConfirmationDialog = null })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimersListContent(
    timersListState: State<List<TimerItemData>>,
    onConfirmTimer: (id: Long) -> Unit,
    onAddTimer: () -> Unit,
    onDeleteTimer: (timer: TimerItemData) -> Unit,
    onResetTimers: () -> Unit
) {
    var showConfetti by remember { mutableStateOf(false) }
    val party = remember {
        Party(
            emitter = Emitter(duration = 3.0.seconds).perSecond(100),
            spread = 45,
            angle = Angle.TOP,
            position = Position.Relative(0.5, 1.0),
            speed = 140f
        )
    }

    Box(Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize()) {
        Scaffold(
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
                        .copy(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    title = {
                        Text(
                            text = stringResource(Res.string.when_did_i_do_something),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.align(BottomEnd).systemBarsPadding().wrapContentSize().padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.secondary,
                    onClick = onAddTimer
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(Res.string.add_timer))
                }
            }) { innerPadding ->
            val lazyListState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = CenterHorizontally,
                state = lazyListState
            ) {
                items(items = timersListState.value, key = { item -> item.id }) { timer ->
                    DraggableItem(
                        modifier = Modifier.fillMaxWidth().animateItem(),
                        scrollState = lazyListState,
                        firstContent = { modifier ->
                            TimerItem(modifier = modifier, timerUI = timer, onConfirmIntake = {
                                onConfirmTimer(timer.id)
                                showConfetti = true
                            })
                        },
                        secondContent = { modifier ->
                            Row(
                                modifier = modifier
                                    .align(Alignment.CenterEnd)
                                    .defaultMinSize(minWidth = 80.dp)
                                    .wrapContentWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { onDeleteTimer(timer) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(Res.string.delete)
                                    )
                                }
                            }
                        }, offsetSize = 80.dp
                    )
                }
                item {
                    OutlinedButton(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 24.dp)
                            .navigationBarsPadding(), onClick = onResetTimers
                    ) {
                        Text(text = stringResource(Res.string.reset_all), fontSize = 15.sp)
                    }
                }
            }
        }

        if (showConfetti) {
            ConfettiKit(
                modifier = Modifier.fillMaxSize(),
                parties = listOf(party),
                onParticleSystemEnded = { _, _ ->
                    showConfetti = false
                },
            )
        }
    }
}

@Composable
@Preview
private fun TimersListViewModelPreview() {
    TimersListContent(
        timersListState = mutableStateOf(TimersMocked.timers),
        onConfirmTimer = {},
        onResetTimers = {},
        onAddTimer = {},
        onDeleteTimer = {}
    )
}
