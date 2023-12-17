package com.android.citypulse.feature_event.domain.use_case

import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.EventRepository

class GetEventUseCase(
    private val repository: EventRepository
) {

    suspend operator fun invoke(id: Int): Event? {
        return repository.getEventById(id)
    }
}