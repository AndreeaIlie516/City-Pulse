package com.android.citypulse.feature_event.domain.use_case

import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.EventRepository

class DeleteEventUseCase(
    private val repository: EventRepository
) {

    suspend operator fun invoke(event: Event) {
        repository.deleteEvent(event)
    }
}