package com.android.citypulse.feature_event.presentation.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.citypulse.R
import com.android.citypulse.feature_event.presentation.events.components.EventCellMainScreen

@Composable
fun PopularEventsScreen(
    modifier: Modifier = Modifier,
    eventViewModel: EventsViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
    ) {
        SetScreenTitle(modifier = modifier)
        EventsList(eventViewModel = eventViewModel, modifier = modifier)
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
                text = stringResource(id = R.string.popular_events_screen),
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
    eventViewModel: EventsViewModel,
    modifier: Modifier = Modifier
) {
    val state = eventViewModel.state.value
    LazyColumn(
        modifier = modifier
    ) {
        items(state.events) { event ->
            if (!event.is_private) {
                EventCellMainScreen(
                    modifier = modifier,
                    event = event,
                    onClickEvent = {},
                    onClickFavoriteEvent = {
                        eventViewModel.onToggleFavourite(event)
                    },
                )
            }
        }

    }
}
