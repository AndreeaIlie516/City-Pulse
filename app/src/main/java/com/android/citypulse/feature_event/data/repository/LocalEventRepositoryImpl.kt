package com.android.citypulse.feature_event.data.repository

import android.util.Log
import com.android.citypulse.feature_event.data.data_source.local.EventDao
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LocalEventRepositoryImpl(
    private val dao: EventDao,
) : LocalEventRepository {

    override fun getEvents(): Flow<List<Event>> {
        return dao.getEvents()
    }

    override suspend fun getEventById(id: Int): Event? {
        return dao.getEventById(id)
    }

    override suspend fun insertEvent(event: Event) {
        dao.insertEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        dao.deleteEvent(event)
    }

    override suspend fun deleteAll() {
        Log.d("EventsRepositoryImpl", "deleteAllEvents called")
        dao.deleteAll()
    }

    override suspend fun clearAndCacheEvents(eventsFlow: Flow<List<Event>>) {
        dao.deleteAll()

        eventsFlow.first().forEach { event ->
            Log.d("EventsRepositoryImpl", "event: $event")
            insertEvent(event)

        }

        Log.d("EventsRepositoryImpl", "events from")
        val eventsFromDb = dao.getEvents()
        Log.d("EventsRepositoryImpl", "events from db: $eventsFromDb")
    }
}