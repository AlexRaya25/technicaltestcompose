package com.rayadev.presentation.ui.components.UserScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.rayadev.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopAppBar(isGridView: Boolean, userViewModel: UserViewModel) {
    TopAppBar(
        title = { Text(text = "Usuarios", fontWeight = FontWeight.Bold) },
        actions = {
            IconButton(onClick = { userViewModel.toggleView() }) {
                Icon(
                    imageVector = if (isGridView) Icons.Rounded.GridView else Icons.Filled.ViewList,
                    contentDescription = "Cambiar vista"
                )
            }
        }
    )
}