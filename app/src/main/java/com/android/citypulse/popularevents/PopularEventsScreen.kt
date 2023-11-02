package com.android.citypulse.popularevents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.citypulse.R
import com.android.citypulse.events.Event
import com.android.citypulse.events.EventCellMain
import com.android.citypulse.events.EventViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PopularEventsScreen(
    eventViewModel: EventViewModel,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        SetScreenTitle(modifier = modifier)
        EventsList(favoriteList = eventViewModel.favoriteList, modifier = modifier)
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

        Image(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 30.dp, top = 8.dp)
                .size(30.dp),
            alignment = Alignment.CenterEnd,
            painter = painterResource(id = R.drawable.filter),
            contentDescription = stringResource(id = R.string.filter_icon_description),
        )
    }
}

@Composable
private fun EventsList(
    favoriteList: SnapshotStateMap<Event, Boolean>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(Event.values()) { event ->
            favoriteList[event]?.let {
                EventCellMain(
                    modifier = modifier,
                    event = event,
                    onClickEvent = {},
                    onClickFavoriteEvent = {
                        favoriteList[event] = !favoriteList[event]!!
                        Log.i("PopularEventsScreen", "Favorites: $favoriteList")
                    },
                    isFavorite = favoriteList[event] ?: false
                )
            }
        }
    }
}
