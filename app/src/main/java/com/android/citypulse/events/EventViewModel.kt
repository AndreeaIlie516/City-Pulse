package com.android.citypulse.events

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {
    val favoriteList = mutableStateMapOf<PredefinedEvent, Boolean>().also { favoriteList ->
        PredefinedEvent.values().forEach { event ->
            favoriteList[event] = false
        }
    }

    val events = MutableLiveData<List<PrivateEvent>>(emptyList())

    fun addEvent(event: PrivateEvent) {
        val currentEvents = events.value ?: emptyList()
        events.value = currentEvents + event
    }

    var privateEventToEdit = PrivateEvent("", "", "", "")

    fun updateEvent(oldEvent: PrivateEvent, newEvent: PrivateEvent) {
        val currentEvents = events.value ?: emptyList()
        events.value = currentEvents - oldEvent + newEvent
    }

    fun deleteEvent(event: PrivateEvent) {
        val currentEvents = events.value ?: emptyList()
        events.value = currentEvents - event
    }
}
