package com.android.citypulse.favoriteevents

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.citypulse.R
import com.android.citypulse.events.Event
import com.android.citypulse.events.EventCellFavorite
import com.android.citypulse.events.EventViewModel
import java.util.concurrent.CancellationException

@Composable
fun FavoriteEventsScreen(
    eventViewModel: EventViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Column(
        modifier = modifier
    ) {
        SetScreenTitle(modifier = modifier)
        EventsList(
            favoriteList = eventViewModel.favoriteList,
            modifier = modifier,
            navController = navController
        )
    }
}

@Composable
fun SetScreenTitle(modifier: Modifier = Modifier) {
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
                text = stringResource(id = R.string.favorite_events_screen),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )
        }
    }
}

@Composable
private fun EventsList(
    favoriteList: SnapshotStateMap<Event, Boolean>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(Event.values()) { event ->
                if (favoriteList[event] == true) {
                    EventCellFavorite(
                        modifier = modifier,
                        event = event,
                        onClickEvent = {},
                        onClickEditEvent = {},
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
        AddButton(
            modifier = Modifier
                .align(BottomEnd)
                .padding(bottom = 45.dp, end = 10.dp),
            navController = navController
        )
    }
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val touchedDown = remember { mutableStateOf(false) }

    var backgroundColor = colorResource(id = R.color.purple)

    if (touchedDown.value) {
        backgroundColor = colorResource(id = R.color.purple_700)
    }

    Box(
        modifier = modifier,
        contentAlignment = BottomEnd
    ) {
        Card(
            modifier = modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            // log
                            touchedDown.value = true

                            val released = try {
                                tryAwaitRelease()
                            } catch (c: CancellationException) {
                                false
                            }

                            if (released) {
                                //log

                                touchedDown.value = false
                                navController.navigate("add")
                            }
                        }
                    )
                }
                .size(50.dp)
                .align(BottomEnd),
            shape = AbsoluteRoundedCornerShape(
                topLeft = 15.dp,
                topRight = 15.dp,
                bottomLeft = 15.dp,
                bottomRight = 15.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 13.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.add_button_label),
                    color = colorResource(id = R.color.white),
                    style = TextStyle(
                        fontSize = 35.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
                    ),
                )
            }
        }
    }
}