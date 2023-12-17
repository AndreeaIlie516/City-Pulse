package com.android.citypulse.feature_event.domain.use_case

import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.model.InvalidEventException
import com.android.citypulse.feature_event.domain.repository.EventRepository

class AddEventUseCase(
    private val repository: EventRepository
) {

    @Throws(InvalidEventException::class)
    suspend operator fun invoke(event: Event) {
        if (event.time.isBlank()) {
            throw InvalidEventException("The time of the event can't be empty.")
        }
        if (event.band.isBlank()) {
            throw InvalidEventException("The band of the event can't be empty.")
        }
        if (event.location.isBlank()) {
            throw InvalidEventException("The location of the event can't be empty.")
        }
        repository.insertEvent(event)
    }
}