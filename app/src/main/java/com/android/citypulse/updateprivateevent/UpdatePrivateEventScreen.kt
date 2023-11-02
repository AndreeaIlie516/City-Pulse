package com.android.citypulse.updateprivateevent

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.citypulse.R
import com.android.citypulse.addprivateevent.SetScreenTitle
import com.android.citypulse.events.EventViewModel
import com.android.citypulse.events.PrivateEvent

@Composable
fun UpdatePrivateEventScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    eventViewModel: EventViewModel
) {
    Column(
        modifier = modifier
    ) {
        SetScreenTitle(
            modifier = modifier,
            stringResource(id = R.string.update_private_event_screen)
        )
        FieldsList(modifier = modifier, navController = navController, eventViewModel)
    }
}


@Composable
fun FieldsList(
    modifier: Modifier = Modifier,
    navController: NavController,
    eventViewModel: EventViewModel
) {
    var time = eventViewModel.privateEventToEdit.time
    var band = eventViewModel.privateEventToEdit.band
    var location = eventViewModel.privateEventToEdit.location
    var photo = eventViewModel.privateEventToEdit.photo

    val oldEvent = PrivateEvent(time, band, location, photo)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
        ) {
            time = timeTextField(time)

            band = bandTextField(band)

            location = locationTextField(location)

            photo = photoTextField(photo)
        }

        Box(
            modifier = modifier
                .align(Alignment.Center)
                .padding(top = 450.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .align(Alignment.BottomEnd),
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
                    val newEvent = PrivateEvent(
                        time,
                        band,
                        location,
                        photo
                    )
                    eventViewModel.updateEvent(oldEvent, newEvent)
                    //eventViewModel.events.value = eventViewModel.events.value?.plus(newEvent)
                    Log.i("UpdatePrivateEventScreen", "Update Private Events button clicked")
                    Log.i("UpdatePrivateEventScreen", "events: ${eventViewModel.events.value}")
                    navController.navigate("favorites")
                }
            ) {
                Text(
                    text = stringResource(id = R.string.update_private_event_button_label),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timeTextField(previousText: String): String {
    var text by remember { mutableStateOf(TextFieldValue(previousText)) }
    OutlinedTextField(
        value = text,
        label = { Text("Time") },
        onValueChange = { newText ->
            text = newText
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    return text.text
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bandTextField(previousText: String): String {
    var text by remember { mutableStateOf(TextFieldValue(previousText)) }
    OutlinedTextField(
        value = text,
        label = { Text("Band") },
        onValueChange = { newText ->
            text = newText
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    return text.text
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun locationTextField(previousText: String): String {
    var text by remember { mutableStateOf(TextFieldValue(previousText)) }
    OutlinedTextField(
        value = text,
        label = { Text("Location") },
        onValueChange = { newText ->
            text = newText
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    return text.text
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun photoTextField(previousText: String): String {
    var text by remember { mutableStateOf(TextFieldValue(previousText)) }
    OutlinedTextField(
        value = text,
        label = { Text("Image URL (optional)") },
        onValueChange = { newText ->
            text = newText
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    return text.text
}