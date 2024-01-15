package com.android.citypulse.feature_event.domain.use_case

import android.util.Log
import com.android.citypulse.feature_event.data.network.NetworkChecker
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository

class ToggleFavouriteStatus(
    private val localRepository: LocalEventRepository,
    private val remoteRepository: RemoteEventRepository,
    private val networkChecker: NetworkChecker
) {
    suspend operator fun invoke(event: Event) {
        Log.d("ToggleFavouriteStatus", "event: $event")
        val updatedEvent = event.copy(is_favourite = !event.is_favourite)
        localRepository.insertEvent(updatedEvent)
        if (networkChecker.isNetworkAvailable()) {
            try {
                localRepository.insertEvent(
                    event.copy(
                        is_favourite = !event.is_favourite,
                        action = null
                    )
                )
                if (event.is_favourite) {
                    remoteRepository.deleteEventFromFavorites(event)
                } else {
                    remoteRepository.addEventToFavorites(event)
                }
            } catch (e: Exception) {
                if (event.is_favourite) {
                    localRepository.insertEvent(
                        event.copy(
                            is_favourite = false,
                            action = "add_to_favourites"
                        )
                    )
                } else {
                    localRepository.insertEvent(
                        event.copy(
                            is_favourite = true,
                            action = "delete_from_favourites"
                        )
                    )
                }
            }
        } else {
            if (event.is_favourite) {
                localRepository.insertEvent(
                    event.copy(
                        is_favourite = false,
                        action = "add_to_favourites"
                    )
                )
            } else {
                localRepository.insertEvent(
                    event.copy(
                        is_favourite = true,
                        action = "delete_from_favourites"
                    )
                )
            }
        }
    }
}