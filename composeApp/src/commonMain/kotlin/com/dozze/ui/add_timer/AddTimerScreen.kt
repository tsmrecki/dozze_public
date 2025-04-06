package com.dozze.ui.add_timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dozze.ui.add_timer.model.AddTimerUI
import com.dozze.ui.components.timer_item.TimerItem
import com.dozze.ui.components.timer_item.TimerItemData
import dozze.composeapp.generated.resources.Res
import dozze.composeapp.generated.resources.add_timer
import dozze.composeapp.generated.resources.confirm_action
import dozze.composeapp.generated.resources.go_back
import dozze.composeapp.generated.resources.name
import dozze.composeapp.generated.resources.preview
import dozze.composeapp.generated.resources.save
import dozze.composeapp.generated.resources.success_action
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddTimerScreen(viewModel: AddTimerViewModel = koinViewModel(), onSaved: () -> Unit, onBack: () -> Unit) {
    val addTimerState = viewModel.addTimer.collectAsStateWithLifecycle()
    val previewTimerState = viewModel.timerPreview.collectAsStateWithLifecycle()

    AddTimerContent(
        addTimerState = addTimerState,
        timerPreviewState = previewTimerState,
        onNameUpdate = viewModel::updateName,
        onConfirmActionUpdate = viewModel::updateConfirmAction,
        onSuccessActionUpdate = viewModel::updateSuccessAction,
        onPreviewConfirm = viewModel::previewConfirm,
        onSave = {
            viewModel.save()
            onSaved()
        },
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimerContent(
    addTimerState: State<AddTimerUI>,
    timerPreviewState: State<TimerItemData?>,
    onNameUpdate: (String) -> Unit,
    onConfirmActionUpdate: (String) -> Unit,
    onSuccessActionUpdate: (String) -> Unit,
    onPreviewConfirm: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    val timerPreview by timerPreviewState
    val addTimer by addTimerState

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            title = {
                Text(
                    text = stringResource(Res.string.add_timer),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(Res.string.go_back)
                    )
                }
            }
        )
    }) { innerPadding ->
        val verticalScroll = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(verticalScroll)
                .padding(16.dp)
        ) {
            timerPreview?.let {
                TimerItem(timerUI = it, onConfirmIntake = { onPreviewConfirm() })
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp),
                    text = stringResource(Res.string.preview),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = addTimer.name,
                label = { Text(stringResource(Res.string.name)) },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                onValueChange = onNameUpdate
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = addTimer.confirmAction ?: "",
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                label = { Text(stringResource(Res.string.confirm_action)) },
                onValueChange = onConfirmActionUpdate
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = addTimer.successAction ?: "",
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                label = { Text(stringResource(Res.string.success_action)) },
                onValueChange = onSuccessActionUpdate
            )
            Spacer(Modifier.height(16.dp))
            ElevatedButton(modifier = Modifier.fillMaxWidth(), onClick = onSave, enabled = addTimer.canSave) {
                Text(stringResource(Res.string.save))
            }
        }
    }
}