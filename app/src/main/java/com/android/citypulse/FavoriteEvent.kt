package com.android.citypulse

data class FavoriteEvent (
    val favorites: MutableMap<Event, Boolean> = mutableMapOf()
){
}