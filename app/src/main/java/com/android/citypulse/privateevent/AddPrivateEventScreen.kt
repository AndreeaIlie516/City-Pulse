package com.android.citypulse.privateevent

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.citypulse.R
import com.android.citypulse.events.EventViewModel
import com.android.citypulse.events.PrivateEvent

@Composable
fun AddPrivateEventScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    eventViewModel: EventViewModel
) {
    Column(
        modifier = modifier
    )
    {
        SetScreenTitle(
            modifier = modifier,
            stringResource(id = R.string.add_private_event_screen)
        )
        FieldsList(modifier = modifier, navController = navController, eventViewModel)
    }


}


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

@Composable
fun FieldsList(
    modifier: Modifier = Modifier,
    navController: NavController,
    eventViewModel: EventViewModel
) {
    var time = ""
    var band = ""
    var location = ""
    var photo = ""
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
        ) {
            time = timeTextField()

            band = bandTextField()

            location = locationTextField()

            photo = photoTextField()
        }

        Box(
            modifier = modifier
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
                    val newEvent = PrivateEvent(
                        time,
                        band,
                        location,
                        photo
                    )
                    eventViewModel.addEvent(newEvent)
                    //eventViewModel.events.value = eventViewModel.events.value?.plus(newEvent)
                    Log.i("AddPrivateEventScreen", "Add Private Events button clicked")
                    Log.i("AddPrivateEventScreen", "events: ${eventViewModel.events.value}")
                    navController.navigate("favorites")
                }
            ) {
                Text(
                    text = stringResource(id = R.string.add_private_event_button_label),
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
fun timeTextField(): String {
    var text by remember { mutableStateOf(TextFieldValue("")) }
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
fun bandTextField(): String {
    var text by remember { mutableStateOf(TextFieldValue("")) }
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
fun locationTextField(): String {
    var text by remember { mutableStateOf(TextFieldValue("")) }
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
fun photoTextField(): String {
    var text by remember { mutableStateOf(TextFieldValue("")) }
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