package com.android.citypulse.feature_event.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.citypulse.feature_event.domain.model.Event


@Database(
    entities = [Event::class],
    version = 4
)
abstract class EventDatabase : RoomDatabase() {

    abstract val eventDao: EventDao

    companion object {
        const val DATABASE_NAME = "event_db"
    }
}