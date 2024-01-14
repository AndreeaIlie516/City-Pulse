package com.android.citypulse.feature_event.domain.repository

import com.android.citypulse.feature_event.data.network.WebSocketUpdate
import com.android.citypulse.feature_event.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface RemoteEventRepository {
    suspend fun getEvents(): List<Event>

    suspend fun observeWebSocketEvents(): Flow<WebSocketUpdate>

    suspend fun insertEvent(event: Event)

    suspend fun deleteEvent(event: Event)

    suspend fun updateEvent(event: Event)

    suspend fun addEventToFavorites(event: Event)

    suspend fun deleteEventFromFavorites(event: Event)

    suspend fun sendMessageToWebsocket(action: String, event: Event)
}