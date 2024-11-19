package com.rayadev.presentation.ui.components.UserDetailScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DividerSection() {
    Divider(
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
        thickness = 1.dp,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}
