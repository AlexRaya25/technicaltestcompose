package com.rayadev.technicaltestcompose.navigation

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rayadev.presentation.ui.screens.UserDetailScreen
import com.rayadev.presentation.ui.screens.UserScreen
import com.rayadev.technicaltestcompose.Screen
import com.rayadev.technicaltestcompose.Screen.UserScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(startDestination: String = Screen.UserScreen.route) {
    val navController = rememberNavController()

    SharedTransitionLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController, startDestination = startDestination) {
            val boundsTransform = { from: Rect, to: Rect ->
                tween<Rect>(durationMillis = 700)
            }
            composable(Screen.UserScreen.route) {
                UserScreen(onUserClick = { userId ->
                    navController.navigate("user_detail_screen/$userId")},
                    animatedVisibilityScope = this,
                    boundsTransform = boundsTransform)
            }
            composable(
                "user_detail_screen/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: -1
                UserDetailScreen(
                    userId = userId,
                    animatedVisibilityScope = this,
                    boundsTransform = boundsTransform)
            }
        }
    }
}