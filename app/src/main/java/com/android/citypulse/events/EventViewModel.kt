package com.android.citypulse.events

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {
    val favoriteList = mutableStateMapOf<Event, Boolean>().also { favoriteList ->
        Event.values().forEach { event ->
            favoriteList[event] = false
        }
    }
}