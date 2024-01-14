package com.android.citypulse.feature_event.domain.use_case

import com.android.citypulse.feature_event.data.network.NetworkChecker
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository

class DeleteEventFromFavouritesUseCase(
    private val localRepository: LocalEventRepository,
    private val remoteRepository: RemoteEventRepository,
    private val networkChecker: NetworkChecker
) {
    suspend operator fun invoke(event: Event) {
        if (networkChecker.isNetworkAvailable()) {
            try {
                remoteRepository.deleteEventFromFavorites(event)
                localRepository.insertEvent(
                    event.copy(
                        is_favourite = false,
                        action = null
                    )
                )
            } catch (e: Exception) {
                localRepository.insertEvent(
                    event.copy(
                        is_favourite = false,
                        action = "pending_delete_from_favourites"
                    )
                )
            }
        } else {
            localRepository.insertEvent(
                event.copy(
                    is_favourite = false,
                    action = "pending_delete_from_favourites"
                )
            )
        }
    }
}