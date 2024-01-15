package com.android.citypulse.feature_event.data.repository

import android.util.Log
import com.android.citypulse.feature_event.data.data_source.remote.EventApi
import com.android.citypulse.feature_event.data.network.WebSocketEventDataSource
import com.android.citypulse.feature_event.data.network.WebSocketUpdate
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.model.EventServer
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository
import kotlinx.coroutines.flow.Flow

class RemoteEventRepositoryImpl(
    private val eventApi: EventApi,
    private val webSocketEventDataSource: WebSocketEventDataSource
) : RemoteEventRepository {
    override suspend fun getEvents(): List<Event> {
        return try {
            val response = eventApi.getEvents()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList() // or handle errors appropriately
                //throw Exception("Failed to fetch events: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            // Log error and return empty list or use local database
            emptyList()
        }
    }

    override suspend fun observeWebSocketEvents(): Flow<WebSocketUpdate> =
        webSocketEventDataSource.observeWebSocketUpdates()

    override suspend fun insertEvent(event: Event): Event {
        try {
            val eventForServer = EventServer(
                time = event.time,
                band = event.band,
                location = event.location,
                image_url = event.image_url,
                is_private = event.is_private,
                is_favourite = event.is_favourite
            )
            val response = eventApi.createEvent(eventForServer)
            if (response.isSuccessful) {
                return response.body() ?: throw Exception("Failed to retrieve created event")
            } else {
                throw Exception("Failed to create event: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("RemoteEventRepository", "Error inserting event: ${e.message}")
            throw Exception("Server error occurred while creating the event.")
        }
    }

    override suspend fun deleteEvent(event: Event) {
        eventApi.deleteEvent(event.ID)
    }

    override suspend fun updateEvent(event: Event) {
        try {
            val eventForServer = EventServer(
                time = event.time,
                band = event.band,
                location = event.location,
                image_url = event.image_url,
                is_private = event.is_private,
                is_favourite = event.is_favourite
            )
            eventApi.updateEvent(event.ID, eventForServer)
        } catch (e: Exception) {
            Log.e("RemoteEventRepository", "Error updating event: ${e.message}")
            throw Exception("Server error occurred while updating the event.")
        }
    }

    override suspend fun addEventToFavorites(event: Event) {
        eventApi.addToFavourites(event.ID)
    }

    override suspend fun deleteEventFromFavorites(event: Event) {
        eventApi.deleteFromFavourites(event.ID)
    }

    override suspend fun sendMessageToWebsocket(action: String, event: Event) {
        webSocketEventDataSource.sendWebSocketUpdate(action, event)
    }

}