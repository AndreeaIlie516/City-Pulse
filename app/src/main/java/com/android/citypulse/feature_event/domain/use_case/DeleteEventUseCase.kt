package com.android.citypulse.feature_event.domain.use_case

import android.util.Log
import com.android.citypulse.feature_event.data.network.NetworkChecker
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository

class DeleteEventUseCase(
    private val localRepository: LocalEventRepository,
    private val remoteRepository: RemoteEventRepository,
    private val networkChecker: NetworkChecker
) {

    suspend operator fun invoke(event: Event): String {
        if (networkChecker.isNetworkAvailable()) {
            try {
                remoteRepository.deleteEvent(event)
                localRepository.deleteEvent(event)
                return "Success"
            } catch (e: Exception) {
                Log.d("DeleteEventUseCase", "Cannot delete")
                return "Failed"
                //throw Exception("Failed to delete the event. Please try again later.")
            }
        } else {
            Log.d("DeleteEventUseCase", "Not connected. Cannot delete")
            return "Failed"
            //throw Exception("Failed to delete the event. Please try again later.")
        }
    }
}