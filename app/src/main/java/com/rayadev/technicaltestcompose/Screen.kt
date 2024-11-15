package com.rayadev.technicaltestcompose

object Screen {
    val UserScreen = ScreenRoute("user_screen")
    val UserDetailScreen = ScreenRoute("user_detail_screen/{userId}")

    data class ScreenRoute(val route: String)
}