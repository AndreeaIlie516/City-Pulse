package com.android.citypulse

import com.android.citypulse.events.Event

data class FavoriteEvent(
    val favorites: MutableMap<Event, Boolean> = mutableMapOf()
)