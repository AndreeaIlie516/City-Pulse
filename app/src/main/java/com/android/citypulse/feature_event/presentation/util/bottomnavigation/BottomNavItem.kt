package com.android.citypulse.feature_event.presentation.util.bottomnavigation

import com.android.citypulse.R

sealed class BottomNavItem(var title: String, var icon: Int, var screenRoute: String) {
    object Home : BottomNavItem("Home", R.drawable.home_unselected, "home")
    object Favorites : BottomNavItem("Favorites", R.drawable.favorite_unselected, "favorites")
    object Profile : BottomNavItem("Profile", R.drawable.profile_unselected, "profile")

}