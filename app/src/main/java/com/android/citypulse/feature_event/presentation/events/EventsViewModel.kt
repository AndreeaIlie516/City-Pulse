package com.android.citypulse.feature_event.presentation.events

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository
import com.android.citypulse.feature_event.domain.use_case.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel
@Inject
constructor(
    private val eventUseCases: EventUseCases,
    private val localEventRepository: LocalEventRepository,
    private val remoteEventRepository: RemoteEventRepository,
) : ViewModel() {

    private val _state = mutableStateOf(EventsState())
    val state: State<EventsState> = _state

    private var recentlyDeletedEvent: Event? = null

    private var getEventsJob: Job? = null

    val snackbarHostState = SnackbarHostState()

    init {
        getEvents()
        observeWebSocketEvents()
    }

    fun showSnackbarMessage(message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    private fun observeWebSocketEvents() {
        viewModelScope.launch {
            remoteEventRepository.observeWebSocketEvents().collect { webSocketUpdate ->
                // Handle the WebSocket update
                when (webSocketUpdate.action) {
                    "CreatedEvent" -> {
                        viewModelScope.launch {
                            eventUseCases.addEventUseCase(
                                Event(
                                    time = webSocketUpdate.event.time,
                                    band = webSocketUpdate.event.band,
                                    location = webSocketUpdate.event.location,
                                    image_url = webSocketUpdate.event.image_url,
                                    is_favourite = webSocketUpdate.event.is_favourite ?: false,
                                    is_private = webSocketUpdate.event.is_private ?: true,
                                    ID = "0",
                                    idLocal = 0,
                                    action = null
                                )

                            )
                            getEvents()
                        }
                    }

                    "UpdateEvent" -> {
                        viewModelScope.launch {
                            eventUseCases.addEventUseCase(
                                Event(
                                    time = webSocketUpdate.event.time,
                                    band = webSocketUpdate.event.band,
                                    location = webSocketUpdate.event.location,
                                    image_url = webSocketUpdate.event.image_url,
                                    is_favourite = webSocketUpdate.event.is_favourite ?: false,
                                    is_private = webSocketUpdate.event.is_private ?: true,
                                    ID = "0",
                                    idLocal = 0,
                                    action = null
                                )
                            )
                            getEvents()
                        }
                    }

                    "DeleteEvent" -> {
                        viewModelScope.launch {
                            eventUseCases.deleteEventUseCase(
                                Event(
                                    time = webSocketUpdate.event.time,
                                    band = webSocketUpdate.event.band,
                                    location = webSocketUpdate.event.location,
                                    image_url = webSocketUpdate.event.image_url,
                                    is_favourite = webSocketUpdate.event.is_favourite ?: false,
                                    is_private = webSocketUpdate.event.is_private ?: true,
                                    ID = "0",
                                    idLocal = 0,
                                    action = null
                                )
                            )
                            getEvents()
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: EventsEvent): Boolean {
        var isDeleteSuccessful = false
        when (event) {
            is EventsEvent.CreateEvent -> {
                viewModelScope.launch {
                    eventUseCases.addEventUseCase(
                        event.event
                    )
                    getEvents()
                }
            }

            is EventsEvent.DeleteEvent -> {
                viewModelScope.launch {
                    try {
                        val result = eventUseCases.deleteEventUseCase(event.event)
                        //recentlyDeletedEvent = event.event
                        Log.d("EventsViewModel", "On try")
                        if (result == "Success") {
                            Log.d("EventsViewModel", "Success")
                            isDeleteSuccessful = true
                            getEvents()
                        } else {
                            Log.d("EventsViewModel", "On try")
                            showSnackbarMessage("Cannot delete event when offline")
                        }
                    } catch (e: Exception) {
                        Log.d("EventsViewModel", "On catch")
                        showSnackbarMessage("Cannot delete event when offline")
                    }
                }
            }

            is EventsEvent.UpdateEvent -> {
                viewModelScope.launch {
                    eventUseCases.updateEventUseCase(
                        event.event
                    )
                    getEvents()
                }
            }

            is EventsEvent.DeleteEventFromFavourites -> {
                viewModelScope.launch {
                    try {
                        val result = eventUseCases.deleteEventFromFavouritesUseCase(event.event)
                        //recentlyDeletedEvent = event.event
                        Log.d("EventsViewModel", "On try")
                        if (result == "Success") {
                            Log.d("EventsViewModel", "Success")
                            isDeleteSuccessful = true
                            getEvents()
                        } else {
                            Log.d("EventsViewModel", "On try")
                            showSnackbarMessage("Cannot delete event when offline")
                        }
                    } catch (e: Exception) {
                        Log.d("EventsViewModel", "On catch")
                        showSnackbarMessage("Cannot delete event when offline")
                    }
                }
            }

            is EventsEvent.RestoreEvent -> {
                viewModelScope.launch {
                    eventUseCases.addEventUseCase(recentlyDeletedEvent ?: return@launch)
                    recentlyDeletedEvent = null
                }
            }
        }
        return isDeleteSuccessful
    }

    private fun getEvents() {
        Log.d("EventsViewModel", "getEvents() called")
        getEventsJob?.cancel()
        getEventsJob = eventUseCases.getEventsUseCase()
            .onEach { events ->
                _state.value = state.value.copy(
                    events = events
                )
                Log.d("EventsViewModel", "ViewModel state updated with new events: $events")
            }
            .launchIn(viewModelScope)
    }

    suspend fun syncPendingChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            val pendingEvents = localEventRepository.getEventsWithPendingActions()
            pendingEvents.forEach { event ->
                when (event.action) {
                    "add" -> remoteEventRepository.insertEvent(event)
                    "update" -> {
                        remoteEventRepository.updateEvent(event)
                        localEventRepository.updateEvent(event.copy(action = null))
                    }

//                    "delete" -> {
//                        remoteEventRepository.deleteEvent(event)
//                        localEventRepository.deleteEvent(event)
//                    }
                }
                if (event.action == "add" || event.action == "update") {
                    localEventRepository.insertEvent(event.copy(action = null))
                }
            }
        }
    }

    fun onToggleFavourite(event: Event) {
        Log.d("EventsViewModel", "onToggleFavourite")
        viewModelScope.launch {
            eventUseCases.toggleFavouriteStatus(event)
            getEvents()
        }
    }

    fun deleteAllEvents() {
        Log.d("EventsViewModel", "deleteAllEvents called")
        viewModelScope.launch {
            eventUseCases.deleteAllEventsUseCase()
            getEvents()
        }
    }
}