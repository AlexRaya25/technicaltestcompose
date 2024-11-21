package com.rayadev.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rayadev.presentation.ui.components.userScreen.UserContent
import com.rayadev.presentation.ui.components.userScreen.UserTopAppBar
import com.rayadev.presentation.viewmodel.UserViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UserScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform,
    fromDetail: Boolean
) {
    val users by userViewModel.users.collectAsState()
    val currentPage by userViewModel.currentPage.collectAsState()
    val isGridView by userViewModel.isGridView.collectAsState()

    val isLoading by userViewModel.isLoading.collectAsState()
    val errorState by userViewModel.errorState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorState) {
        if (errorState.isNotEmpty()) {
            snackbarHostState.showSnackbar(message = errorState)
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            userViewModel.refreshUsers()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = { Surface(shadowElevation = 3.dp) {UserTopAppBar(isGridView, userViewModel) }},
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            content = { paddingValues ->
                UserContent(
                    users = users,
                    currentPage = currentPage,
                    isGridView = isGridView,
                    onUserClick = onUserClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform,
                    paddingValues = paddingValues,
                    userViewModel = userViewModel,
                    isLoading = isLoading,
                    errorState = errorState,
                    fromDetail = fromDetail
                )
            }
        )
    }
}
