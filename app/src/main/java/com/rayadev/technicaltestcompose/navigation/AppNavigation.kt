package com.rayadev.technicaltestcompose.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rayadev.presentation.ui.screens.UserDetailScreen
import com.rayadev.presentation.ui.screens.UserScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(startDestination: String = "user_screen") {
    val navController = rememberNavController()

    var fromDetail by remember { mutableStateOf(false) }

    SharedTransitionLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController, startDestination = startDestination) {
            val boundsTransform = { _: Rect, _: Rect ->
                tween<Rect>(durationMillis = 700)
            }
            composable("user_screen") {
                UserScreen(onUserClick = { userId ->
                    navController.navigate("user_detail_screen/$userId")},
                    animatedVisibilityScope = this,
                    boundsTransform = boundsTransform,
                    fromDetail = fromDetail)
            }
            composable(
                "user_detail_screen/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: -1
                UserDetailScreen(
                    userId = userId,
                    animatedVisibilityScope = this,
                    boundsTransform = boundsTransform,
                    onNavigateBack = {
                        fromDetail = true
                        navController.popBackStack()
                    })
            }
        }
    }
}