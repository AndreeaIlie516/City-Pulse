package com.android.citypulse.feature_event.presentation.events

import com.android.citypulse.feature_event.domain.model.Event

sealed class EventsEvent {
    data class DeleteEvent(val event: Event) : EventsEvent()
    data class DeleteEventFromFavourites(val event: Event) : EventsEvent()
    object RestoreEvent : EventsEvent()
}