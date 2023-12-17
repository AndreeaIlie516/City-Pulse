package com.android.citypulse.feature_event.presentation.util

sealed class Screen(val route: String) {
    object PopularEventsScreen : Screen("popular_events_screen")
    object FavoriteEventsScreen : Screen("favorite_events_screen")
    object AddEditEventScreen : Screen("add_edit_event_screen")
}