package com.android.citypulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.citypulse.bottomnavigation.BottomNavItem
import com.android.citypulse.events.EventViewModel
import com.android.citypulse.favoriteevents.FavoriteEventsScreen
import com.android.citypulse.popularevents.PopularEventsScreen
import com.android.citypulse.privateevent.AddPrivateEventScreen
import com.android.citypulse.profile.ProfileScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    eventViewModel: EventViewModel
) {
    NavHost(navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            PopularEventsScreen(eventViewModel = eventViewModel)
        }
        composable(BottomNavItem.Favorites.screenRoute) {
            FavoriteEventsScreen(eventViewModel = eventViewModel, navController = navController)
        }
        composable(BottomNavItem.Profile.screenRoute) {
            ProfileScreen()
        }
        composable(NavItem.Add.screenRoute) {
            AddPrivateEventScreen(navController = navController, eventViewModel = eventViewModel)
        }
    }
}
