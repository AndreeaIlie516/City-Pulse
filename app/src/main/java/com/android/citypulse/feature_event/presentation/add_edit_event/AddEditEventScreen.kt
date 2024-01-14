package com.android.citypulse.feature_event.presentation.add_edit_event

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.citypulse.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SetScreenTitle(
    modifier: Modifier = Modifier,
    screenTitle: String
) {
    Row(
        modifier = modifier
            .padding(start = 25.dp, top = 65.dp, bottom = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Column(
            modifier = modifier
        )
        {
            Text(
                text = screenTitle,
                textAlign = TextAlign.Left,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditEventScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddEditEventViewModel = hiltViewModel(),
) {
    val timeState = viewModel.eventTime.value
    val bandState = viewModel.eventBand.value
    val locationState = viewModel.eventLocation.value
    val imagePathState = viewModel.eventImagePath.value

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val eventId = viewModel.currentEventId

    val screenScope = if (eventId == null) {
        "add"
    } else {
        "update"
    }

    Log.d("AddEditEventScreen", "Events: ${viewModel.currentEventId}")

    val titleTextId = if (eventId == null) {
        R.string.add_private_event_screen
    } else {
        R.string.update_private_event_screen
    }
    val buttonTextId = if (eventId == null) {
        R.string.add_private_event_button_label
    } else {
        R.string.update_private_event_button_label
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditEventViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditEventViewModel.UiEvent.SaveNewEvent -> {
                    Log.d("AddEditEventScreen", "Should navigate up")
                    navController.navigateUp()
                }

                is AddEditEventViewModel.UiEvent.SaveUpdatedEvent -> {
                    Log.d("AddEditEventScreen", "Should navigate up")
                    navController.navigateUp()
                }
            }
        }
    }
    Column(
        modifier = modifier
    )
    {
        SetScreenTitle(
            modifier = modifier,
            stringResource(id = titleTextId)
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = modifier
            ) {
                OutlinedTextField(
                    value = timeState.text,
                    label = { Text("Time") },
                    onValueChange = {
                        viewModel.onEvent(AddEditEventEvent.EnteredTime(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = bandState.text,
                    label = { Text("Band") },
                    onValueChange = {
                        viewModel.onEvent(AddEditEventEvent.EnteredBand(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = locationState.text,
                    label = { Text("Location") },
                    onValueChange = {
                        viewModel.onEvent(AddEditEventEvent.EnteredLocation(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = imagePathState.text,
                    label = { Text("ImagePath") },
                    onValueChange = {
                        viewModel.onEvent(AddEditEventEvent.EnteredImagePath(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            Box(
                modifier = Modifier
                    .align(Center)
                    .padding(top = 450.dp),
                contentAlignment = BottomEnd
            ) {
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 15.dp, end = 15.dp)
                        .align(BottomEnd),
                    shape = AbsoluteRoundedCornerShape(
                        topLeft = 15.dp,
                        topRight = 15.dp,
                        bottomLeft = 15.dp,
                        bottomRight = 15.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.purple)
                    ),
                    onClick = {
                        if (screenScope == "add") {
                            viewModel.onEvent(AddEditEventEvent.SaveNewEvent)
                        } else {
                            viewModel.onEvent(AddEditEventEvent.SaveUpdatedEvent)
                        }
                    }
                ) {
                    Text(

                        text = stringResource(id = buttonTextId),
                        color = colorResource(id = R.color.white),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
                        ),
                    )
                }

            }
        }
    }
}

