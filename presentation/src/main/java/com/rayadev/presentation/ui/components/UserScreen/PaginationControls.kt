package com.rayadev.presentation.ui.components.UserScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PaginationControls(
    currentPage: Int,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    canGoToNextPage: Boolean,
    canGoToPreviousPage: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onPreviousPage,
            enabled = canGoToPreviousPage
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Página Anterior"
            )
        }
        Text(
            text = "Página $currentPage",
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = onNextPage,
            enabled = canGoToNextPage
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Página Siguiente"
            )
        }
    }
}