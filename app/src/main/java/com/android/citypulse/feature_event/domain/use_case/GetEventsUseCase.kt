package com.android.citypulse.feature_event.domain.use_case

import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventsUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(): Flow<List<Event>> {
        return repository.getEvents()
    }
}