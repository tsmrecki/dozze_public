package com.dozze.ui.components.timer_item

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dozze.composeapp.generated.resources.Res
import dozze.composeapp.generated.resources.confirm
import dozze.composeapp.generated.resources.congrats
import dozze.composeapp.generated.resources.hours
import dozze.composeapp.generated.resources.just_now
import dozze.composeapp.generated.resources.minutes
import dozze.composeapp.generated.resources.s_ago
import dozze.composeapp.generated.resources.s_s_ago
import dozze.composeapp.generated.resources.start_tracking
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun TimerItem(modifier: Modifier = Modifier, timerUI: TimerItemData, onConfirmIntake: (TimerItemData) -> Unit) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        val timeAgo = timerUI.confirmedAgo?.let { duration ->
            if (duration.hours == 0 && duration.minutes == 0) {
                timerUI.successAction ?: stringResource(Res.string.just_now)
            } else if (duration.hours == 0) {
                stringResource(
                    Res.string.s_ago,
                    pluralStringResource(
                        resource = Res.plurals.minutes,
                        quantity = duration.minutes,
                        duration.minutes
                    )
                )
            } else {
                stringResource(
                    Res.string.s_s_ago,
                    pluralStringResource(
                        resource = Res.plurals.hours,
                        quantity = duration.hours,
                        duration.hours
                    ), pluralStringResource(
                        resource = Res.plurals.minutes,
                        quantity = duration.minutes,
                        duration.minutes
                    )
                )
            }
        } ?: stringResource(Res.string.start_tracking)

        Row(modifier = Modifier.fillMaxWidth().animateContentSize().padding(15.dp)) {
            Column(
                Modifier.weight(1f)
            ) {
                Row {
                    Text(
                        modifier = Modifier
                            .align(CenterVertically),
                        text = timerUI.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Row {
                    Text(text = timeAgo, style = MaterialTheme.typography.bodySmall)
                }
            }

            ElevatedButton(
                modifier = Modifier
                    .align(CenterVertically)
                    .defaultMinSize(minWidth = 120.dp),
                enabled = timerUI.canConfirm,
                onClick = { onConfirmIntake(timerUI) }) {
                Text(
                    text = if (timerUI.canConfirm) timerUI.confirmAction
                        ?: stringResource(Res.string.confirm) else stringResource(Res.string.congrats),
                    fontSize = 16.sp
                )
            }
        }
    }
}
