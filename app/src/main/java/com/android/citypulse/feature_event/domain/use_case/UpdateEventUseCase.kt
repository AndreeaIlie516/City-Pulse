package com.android.citypulse.feature_event.domain.use_case

import android.util.Log
import com.android.citypulse.feature_event.data.network.NetworkChecker
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.model.InvalidEventException
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository

class UpdateEventUseCase(
    private val localRepository: LocalEventRepository,
    private val remoteRepository: RemoteEventRepository,
    private val networkChecker: NetworkChecker
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
        try {
            if (networkChecker.isNetworkAvailable()) {
                remoteRepository.updateEvent(event)
                Log.d("UpdateEventUseCase", "event: $event")
                localRepository.insertEvent(event.copy(action = null))
            } else {
                Log.d("UpdateEventUseCase", "event: $event")
                localRepository.insertEvent(event.copy(ID = event.ID, action = "update"))
            }
        } catch (e: Exception) {
            throw Exception("Failed to update the event. Please try again later.")
        }
    }
}