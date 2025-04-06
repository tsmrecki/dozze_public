package com.dozze.ui.components.draggable_item

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableItem(
    modifier: Modifier,
    firstContent: @Composable (Modifier) -> Unit,
    secondContent: @Composable (Modifier) -> Unit,
    offsetSize: Dp,
    scrollState: ScrollableState? = null
) {
    val initAnchorValue = if (LocalInspectionMode.current) DraggableItemAnchors.End else DraggableItemAnchors.Start
    val density = LocalDensity.current
    val cardOffsetPx = with(density) { offsetSize.toPx() }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = initAnchorValue,
            anchors = DraggableAnchors {
                DraggableItemAnchors.Start at 0f
                DraggableItemAnchors.End at -cardOffsetPx
            },
            positionalThreshold = { distance -> distance * 0.8f },
            velocityThreshold = { with(density) { offsetSize.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec,
        )
    }

    scrollState?.let { state ->
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect("collapse_on_scroll") {
            coroutineScope.launch {
                snapshotFlow { state.isScrollInProgress }
                    .distinctUntilChanged()
                    .collectLatest { isScrollInProgress ->
                        if (isScrollInProgress && anchoredDraggableState.currentValue != DraggableItemAnchors.Start) {
                            coroutineScope.launch {
                                anchoredDraggableState.animateTo(DraggableItemAnchors.Start)
                            }
                        }
                    }
            }
        }

    }


    Box(modifier = modifier.anchoredDraggable(anchoredDraggableState, Orientation.Horizontal)) {
        secondContent(Modifier)
        firstContent(
            Modifier.offset { IntOffset(anchoredDraggableState.requireOffset().roundToInt(), 0) }
        )

    }
}
