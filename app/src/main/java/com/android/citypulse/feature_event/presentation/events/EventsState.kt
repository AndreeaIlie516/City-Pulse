package com.android.citypulse.feature_event.presentation.events

import com.android.citypulse.feature_event.domain.model.Event

data class EventsState(
    val events: List<Event> = emptyList()
)