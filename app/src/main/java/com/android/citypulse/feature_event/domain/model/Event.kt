package com.android.citypulse.feature_event.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val idLocal: Int,
    val ID: String,
    val time: String,
    val band: String,
    val location: String,
    val image_url: String,
    val is_private: Boolean,
    var is_favourite: Boolean,
    var action: String?
)

data class EventServer(
    val time: String,
    val band: String,
    val location: String,
    val image_url: String,
    val is_private: Boolean?,
    val is_favourite: Boolean?
)

class InvalidEventException(message: String) : Exception(message)