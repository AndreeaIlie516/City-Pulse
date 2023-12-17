package com.android.citypulse.feature_event.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val time: String,
    val band: String,
    val location: String,
    val imagePath: String,
    val isPrivate: Boolean,
    var isFavourite: Boolean
)

class InvalidEventException(message: String) : Exception(message)