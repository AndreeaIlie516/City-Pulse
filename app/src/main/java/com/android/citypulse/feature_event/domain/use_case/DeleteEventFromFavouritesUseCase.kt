package com.android.citypulse.feature_event.domain.use_case

import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.EventRepository

class DeleteEventFromFavouritesUseCase(
    private val repository: EventRepository
) {
    suspend operator fun invoke(event: Event) {
        val updatedEvent = event.copy(isFavourite = false)
        repository.insertEvent(updatedEvent)
    }
}