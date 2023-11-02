package com.android.citypulse.navigation

sealed class NavItem(var title: String, var screenRoute: String) {
    object Add : NavItem("Add", "add")

}