package com.android.citypulse.feature_event.domain.use_case

import android.util.Log
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository

class DeleteAllEventsUseCase(
    private val repository: LocalEventRepository
) {

    suspend operator fun invoke() {
        Log.d("deleteAllEventsUseCase", "deleteAllEvents called")
        repository.deleteAll()
    }
}