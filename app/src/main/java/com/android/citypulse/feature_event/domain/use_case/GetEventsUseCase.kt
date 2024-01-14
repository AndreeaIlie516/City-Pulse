package com.android.citypulse.feature_event.domain.use_case

import android.util.Log
import com.android.citypulse.feature_event.data.network.NetworkChecker
import com.android.citypulse.feature_event.domain.model.Event
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class GetEventsUseCase(
    private val localRepository: LocalEventRepository,
    private val remoteRepository: RemoteEventRepository,
    private val networkChecker: NetworkChecker
) {

    operator fun invoke(): Flow<List<Event>> = flow {
        val localEvents = localRepository.getEvents()
        Log.d("GetEventsUseCase", "localEvents: $localEvents")
        if (networkChecker.isNetworkAvailable()) {
            try {
                val remoteEvents = remoteRepository.getEvents()
                Log.d("GetEventsUseCase", "remoteEvents: $remoteEvents")

                val mergedEvents =
                    localEvents.combine(flowOf(remoteEvents)) { flowValue, listValue ->
                        (flowValue + listValue).distinctBy { it.ID }
                    }
                val mergedList = mutableListOf<Event>()
                Log.d("GetEventsUseCase", "merged events: $mergedEvents")
                mergedEvents.onEach { list ->
                    Log.d("GetEventsUseCase", "list on merge: $list")
                    list.onEach { event ->
                        Log.d("GetEventsUseCase", "event on merge: $event")
                        mergedList.add(event)
                    }
                }
                Log.d("GetEventsUseCase", "mergedList: $mergedList")
                localRepository.clearAndCacheEvents(mergedEvents)

                emitAll(mergedEvents)
            } catch (e: Exception) {
                emitAll(localRepository.getEvents())
            }
        } else {
            emitAll(localRepository.getEvents())
        }
    }.flowOn(Dispatchers.IO)
}