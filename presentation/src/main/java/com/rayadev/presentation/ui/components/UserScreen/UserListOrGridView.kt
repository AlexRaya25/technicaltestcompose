package com.rayadev.presentation.ui.components.UserScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rayadev.domain.model.User

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UserListOrGridView(
    users: List<User>,
    isGridView: Boolean,
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform,
    modifier: Modifier = Modifier,
    currentPage: Int,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    canGoToNextPage: Boolean,
    canGoToPreviousPage: Boolean
) {
    val paginationModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)

    if (isGridView) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            itemsIndexed(users) { _, user ->
                UserGridItem(
                    user = user,
                    onUserClick = { onUserClick(user.id) },
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
            }
            item(span = { GridItemSpan(2) }) {
                PaginationControls(
                    currentPage = currentPage,
                    onNextPage = onNextPage,
                    onPreviousPage = onPreviousPage,
                    canGoToNextPage = canGoToNextPage,
                    canGoToPreviousPage = canGoToPreviousPage,
                    modifier = paginationModifier
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(users) { user ->
                UserListItem(
                    user = user,
                    onUserClick = onUserClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
            }
            item {
                PaginationControls(
                    currentPage = currentPage,
                    onNextPage = onNextPage,
                    onPreviousPage = onPreviousPage,
                    canGoToNextPage = canGoToNextPage,
                    canGoToPreviousPage = canGoToPreviousPage,
                    modifier = paginationModifier
                )
            }
        }
    }
}
