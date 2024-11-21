package com.rayadev.presentation.ui.components.userScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
    userViewModel: UserViewModel,
    isLoading: Boolean,
    errorState: String,
    fromDetail: Boolean
) {

    Box(modifier = Modifier.fillMaxSize()) {
        if (!isLoading && errorState.isEmpty()) {
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
                canGoToPreviousPage = userViewModel.canGoToPreviousPage(),
                errorState = errorState,
                fromDetail = fromDetail
            )
        } else {
            ShimmerLoading(
                isGridView,
                modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues))
        }
    }
}

