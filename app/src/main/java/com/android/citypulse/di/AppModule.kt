package com.android.citypulse.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android.citypulse.feature_event.data.data_source.local.EventDatabase
import com.android.citypulse.feature_event.data.data_source.remote.EventApi
import com.android.citypulse.feature_event.data.network.NetworkChecker
import com.android.citypulse.feature_event.data.network.WebSocketEventDataSource
import com.android.citypulse.feature_event.data.repository.LocalEventRepositoryImpl
import com.android.citypulse.feature_event.data.repository.RemoteEventRepositoryImpl
import com.android.citypulse.feature_event.domain.repository.LocalEventRepository
import com.android.citypulse.feature_event.domain.repository.RemoteEventRepository
import com.android.citypulse.feature_event.domain.use_case.AddEventUseCase
import com.android.citypulse.feature_event.domain.use_case.DeleteAllEventsUseCase
import com.android.citypulse.feature_event.domain.use_case.DeleteEventFromFavouritesUseCase
import com.android.citypulse.feature_event.domain.use_case.DeleteEventUseCase
import com.android.citypulse.feature_event.domain.use_case.EventUseCases
import com.android.citypulse.feature_event.domain.use_case.GetEventUseCase
import com.android.citypulse.feature_event.domain.use_case.GetEventsUseCase
import com.android.citypulse.feature_event.domain.use_case.ToggleFavouriteStatus
import com.android.citypulse.feature_event.domain.use_case.UpdateEventUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEventDatabase(app: Application): EventDatabase {
        return Room.databaseBuilder(
            app,
            EventDatabase::class.java,
            EventDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkChecker(context)
    }

    @Singleton
    @Provides
    fun provideWebSocketEventDataSource(networkChecker: NetworkChecker): WebSocketEventDataSource {
        val webSocketUrl = "ws://10.0.2.2:6000/ws"
        return WebSocketEventDataSource(networkChecker, webSocketUrl)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:6000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideEventApi(retrofit: Retrofit): EventApi {
        return retrofit.create(EventApi::class.java)
    }

    @Provides
    fun provideLocalEventRepository(
        db: EventDatabase,
    ): LocalEventRepository {
        return LocalEventRepositoryImpl(db.eventDao)
    }

    @Provides
    fun provideRemoteEventRepository(
        eventApi: EventApi,
        webSocketEventDataSource: WebSocketEventDataSource
    ): RemoteEventRepository {
        return RemoteEventRepositoryImpl(eventApi, webSocketEventDataSource)
    }

    @Provides
    @Singleton
    fun provideEventUseCases(
        localRepository: LocalEventRepository,
        remoteRepository: RemoteEventRepository,
        networkChecker: NetworkChecker
    ): EventUseCases {
        return EventUseCases(
            getEventsUseCase = GetEventsUseCase(
                localRepository = localRepository,
                remoteRepository = remoteRepository,
                networkChecker = networkChecker
            ),
            getEventUseCase = GetEventUseCase(localRepository),
            addEventUseCase = AddEventUseCase(
                localRepository = localRepository,
                remoteRepository = remoteRepository,
                networkChecker = networkChecker
            ),
            deleteEventUseCase = DeleteEventUseCase(
                localRepository = localRepository,
                remoteRepository = remoteRepository,
                networkChecker = networkChecker
            ),
            deleteAllEventsUseCase = DeleteAllEventsUseCase(localRepository),
            toggleFavouriteStatus = ToggleFavouriteStatus(
                localRepository = localRepository,
                remoteRepository = remoteRepository,
                networkChecker = networkChecker
            ),
            deleteEventFromFavouritesUseCase = DeleteEventFromFavouritesUseCase(
                localRepository = localRepository,
                remoteRepository = remoteRepository,
                networkChecker = networkChecker
            ),
            updateEventUseCase = UpdateEventUseCase(
                localRepository = localRepository,
                remoteRepository = remoteRepository,
                networkChecker = networkChecker
            )
        )
    }
}