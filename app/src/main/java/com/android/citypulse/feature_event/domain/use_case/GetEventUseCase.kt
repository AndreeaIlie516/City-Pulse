package com.android.citypulse.feature_event.domain.use_case

import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository

class GetEventUseCase(
    private val repository: LocalEventRepository
) {

    suspend operator fun invoke(id: Int): Event? {
        return repository.getEventById(id)
    }
}