package com.android.citypulse.feature_event.data.data_source.local

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.citypulse.feature_event.domain.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getEvents(): Flow<List<Event>>

    @Query("SELECT * FROM event WHERE ID = :id")
    suspend fun getEventById(id: Int): Event?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Query("Delete FROM event")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteEvent(event: Event)

    @Transaction
    suspend fun clearAndCacheEvents(events: List<Event>) {
        deleteAll()
        Log.d("EventDao", "events: $events")
        events.forEach { event ->
            Log.d("EventDao", "event: $event")
            insertEvent(event)
        }
        val eventsInDb = getEvents()
        Log.d("EventDao", "events in db: $eventsInDb")
    }
}