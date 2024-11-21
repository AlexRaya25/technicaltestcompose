package com.rayadev.presentation.ui.components.userScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoading(
    isGridView: Boolean,
    modifier: Modifier = Modifier,
) {
    if (isGridView) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(6) {
                UserGridItemShimmer()
            }
        }
    } else {
        LazyColumn(modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)) {
            items(6) {
                UserListItemShimmer()
            }
        }
    }
}