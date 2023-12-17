package com.android.citypulse.feature_event.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.citypulse.feature_event.domain.model.Event


@Database(
    entities = [Event::class],
    version = 2
)
abstract class EventDatabase : RoomDatabase() {

    abstract val eventDao: EventDao

    companion object {
        const val DATABASE_NAME = "event_db"
    }
}