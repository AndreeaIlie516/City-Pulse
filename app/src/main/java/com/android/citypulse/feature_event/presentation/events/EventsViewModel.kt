package com.android.citypulse.feature_event.presentation.events

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.use_case.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel
@Inject
constructor(
    private val eventUseCases: EventUseCases
) : ViewModel() {

    private val _state = mutableStateOf(EventsState())
    val state: State<EventsState> = _state

    private var recentlyDeletedEvent: Event? = null

    private var getEventsJob: Job? = null

    init {
        getEvents()
        initializeDefaultEvents()
    }

    fun onEvent(event: EventsEvent) {
        when (event) {
            is EventsEvent.DeleteEvent -> {
                viewModelScope.launch {
                    eventUseCases.deleteEventUseCase(
                        event.event
                    )
                    recentlyDeletedEvent = event.event
                    getEvents()
                }
            }

            is EventsEvent.DeleteEventFromFavourites -> {
                viewModelScope.launch {
                    eventUseCases.deleteEventFromFavouritesUseCase(
                        event.event
                    )
                    getEvents()
                }
            }

            is EventsEvent.RestoreEvent -> {
                viewModelScope.launch {
                    eventUseCases.addEventUseCase(recentlyDeletedEvent ?: return@launch)
                    recentlyDeletedEvent = null
                }
            }
        }
    }

    private fun getEvents() {
        getEventsJob?.cancel()
        getEventsJob = eventUseCases.getEventsUseCase()
            .onEach { events ->
                _state.value = state.value.copy(
                    events = events
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getDefaultEvents(): List<Event> {
        return listOf(
        )
    }

    private fun initializeDefaultEvents() {
        viewModelScope.launch {
            val defaultEvents = getDefaultEvents()
            defaultEvents.forEach { event ->
                eventUseCases.addEventUseCase(event)
            }
        }
    }

    fun onToggleFavourite(event: Event) {
        viewModelScope.launch {
            eventUseCases.toggleFavouriteStatus(event)
            getEvents()
        }
    }
}