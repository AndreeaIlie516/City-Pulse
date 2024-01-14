package com.android.citypulse.feature_event.presentation.favorite_events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.citypulse.R
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.presentation.events.EventsEvent
import com.android.citypulse.feature_event.presentation.events.EventsViewModel
import com.android.citypulse.feature_event.presentation.events.components.AddButton
import com.android.citypulse.feature_event.presentation.events.components.DeleteItem
import com.android.citypulse.feature_event.presentation.util.Screen


@Composable
fun FavoriteEventsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    eventViewModel: EventsViewModel = hiltViewModel(),
) {

    val state = eventViewModel.state.value

    Column(
        modifier = modifier
    ) {
        SetScreenTitle(modifier = modifier)
        state.events.let {
            EventsList(
                eventList = it,
                eventViewModel = eventViewModel,
                modifier = modifier,
                navController = navController
            )
        }
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
    eventList: List<Event>,
    eventViewModel: EventsViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
        ) {
            val filteredEvents = eventList.filter { it.is_favourite || it.is_private }
            items(filteredEvents) { event ->
                DeleteItem(event = event,
                    onClickEvent = {},
                    onClickEditEvent = {
                        navController.navigate(
                            Screen.AddEditEventScreen.route +
                                    "?eventId=${event.ID}"
                        )
                    },
                    onDelete = {
                        if (event.is_private) {
                            eventViewModel.onEvent(EventsEvent.DeleteEvent(event))
                        } else {
                            eventViewModel.onEvent(EventsEvent.DeleteEventFromFavourites(event))
                        }
                    }
                )
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