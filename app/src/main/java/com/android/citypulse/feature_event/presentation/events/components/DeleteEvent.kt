package com.android.citypulse.feature_event.presentation.events.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.citypulse.feature_event.domain.model.Event


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItem(
    modifier: Modifier = Modifier,
    event: Event,
    onClickEvent: () -> Unit,
    onClickEditEvent: () -> Unit,
    onDelete: () -> Boolean // Returns a Boolean indicating success or failure
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var show by remember { mutableStateOf(true) }
    val dismissState = rememberDismissState(confirmValueChange = {
        it == DismissValue.DismissedToStart
    }, positionalThreshold = { 150.dp.toPx() })

    AnimatedVisibility(
        visible = show,
        exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = modifier,
            background = { DismissBackground(dismissState) },
            dismissContent = {
                EventCellFavoriteScreen(
                    event = event,
                    onClickEvent = onClickEvent,
                    onClickEditEvent = onClickEditEvent
                )
            }
        )
    }

    LaunchedEffect(key1 = dismissState.currentValue) {
        if (dismissState.currentValue == DismissValue.DismissedToStart) {
            showDialog = true
            dismissState.reset()
        }
    }

    if (showDialog) {
        ShowConfirmationDialog(onConfirm = {
            if (onDelete()) { // Check if delete was successful
                show = false
                Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
                // Hide the item if delete was successful
            } else {
                Toast.makeText(context, "Cannot delete event when offline", Toast.LENGTH_SHORT)
                    .show()
            }
            showDialog = false
        }, onDismiss = {
            showDialog = false
        })
    }
}
