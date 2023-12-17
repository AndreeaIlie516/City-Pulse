package com.android.citypulse.feature_event.data.repository

import com.android.citypulse.feature_event.data.data_source.EventDao
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val dao: EventDao
) : EventRepository {
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
}