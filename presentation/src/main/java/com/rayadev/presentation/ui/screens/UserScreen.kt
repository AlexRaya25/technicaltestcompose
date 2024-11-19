package com.rayadev.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadev.presentation.ui.components.UserScreen.UserContent
import com.rayadev.presentation.ui.components.UserScreen.UserTopAppBar
import com.rayadev.presentation.viewmodel.UserViewModel


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.UserScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    val users by userViewModel.users.collectAsState()
    val currentPage by userViewModel.currentPage.collectAsState()
    val isGridView by userViewModel.isGridView.collectAsState()

    Scaffold(
        topBar = { UserTopAppBar(isGridView, userViewModel) },
        content = { paddingValues ->
            UserContent(
                users = users,
                currentPage = currentPage,
                isGridView = isGridView,
                onUserClick = onUserClick,
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform,
                paddingValues = paddingValues,
                userViewModel = userViewModel
            )
        }
    )
}