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
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            PopularEventsScreen(eventViewModel = eventViewModel)
        }
        composable(BottomNavItem.Favorites.screen_route) {
            FavoriteEventsScreen(eventViewModel = eventViewModel, navController = navController)
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen()
        }
        composable(NavItem.Add.screen_route) {
            AddPrivateEventScreen()
        }
    }
}
