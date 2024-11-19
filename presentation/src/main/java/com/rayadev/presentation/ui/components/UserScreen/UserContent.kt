package com.rayadev.presentation.ui.components.UserScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rayadev.domain.model.User
import com.rayadev.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UserContent(
    users: List<User>,
    currentPage: Int,
    isGridView: Boolean,
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel
) {
    if (users.isEmpty()) {
        LoadingIndicator()
    } else {
        UserListOrGridView(
            users = users,
            isGridView = isGridView,
            onUserClick = onUserClick,
            animatedVisibilityScope = animatedVisibilityScope,
            boundsTransform = boundsTransform,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            currentPage = currentPage,
            onNextPage = { userViewModel.nextPage() },
            onPreviousPage = { userViewModel.previousPage() },
            canGoToNextPage = userViewModel.canGoToNextPage(),
            canGoToPreviousPage = userViewModel.canGoToPreviousPage()
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}