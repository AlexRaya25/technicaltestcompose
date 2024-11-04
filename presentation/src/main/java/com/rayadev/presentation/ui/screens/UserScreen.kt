package com.rayadev.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rayadev.presentation.viewmodel.UserViewModel

@Composable
fun UserScreen(userViewModel: UserViewModel = hiltViewModel()) {
    val users = userViewModel.users.collectAsState().value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            users.forEach { user ->
                Text(text = "${user.first_name} ${user.last_name}")
            }
        }
        Text(text = "Hola Mundo")
    }
}