package com.example.readerapp.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateLaunchSingleTop(route: Any) {
    navigate(
        route = route,
    ) {
        launchSingleTop = true
    }
}