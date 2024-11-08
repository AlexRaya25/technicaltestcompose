package com.rayadev.technicaltestcompose.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rayadev.presentation.ui.screens.UserDetailScreen
import com.rayadev.presentation.ui.screens.UserScreen
import com.rayadev.technicaltestcompose.Screen

@Composable
fun AppNavigation(startDestination: String = Screen.UserScreen.route) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {
        composable(
            route = Screen.UserScreen.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ) {
            UserScreen(
                onUserClick = { userId, imagePosition, imageSize ->
                    navController.navigate("user_detail_screen/$userId/${imagePosition.x},${imagePosition.y},${imageSize.width},${imageSize.height}")
                }
            )
        }
        composable(
            route = Screen.UserDetailScreen.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("imagePosition") { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) + fadeOut()
            }
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: -1
            val imagePosition = backStackEntry.arguments?.getString("imagePosition")?.split(",")?.map { it.toFloat() }?.let {
                Offset(it[0], it[1])
            } ?: Offset(0f, 0f)
            val imageSize = backStackEntry.arguments?.getString("imagePosition")?.split(",")?.let {
                IntSize(it[2].toInt(), it[3].toInt())
            } ?: IntSize(56, 56)

            UserDetailScreen(userId = userId, imagePosition = imagePosition, imageSize = imageSize)
        }
    }
}