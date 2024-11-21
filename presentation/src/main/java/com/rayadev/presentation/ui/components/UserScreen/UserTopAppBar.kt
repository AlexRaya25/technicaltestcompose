package com.rayadev.presentation.ui.components.userScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.rayadev.presentation.R
import com.rayadev.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopAppBar(isGridView: Boolean, userViewModel: UserViewModel) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.users),
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize)
            )
        },
        actions = {
            IconButton(onClick = { userViewModel.toggleView() }) {
                Icon(
                    imageVector = if (isGridView) Icons.Rounded.GridView else Icons.AutoMirrored.Filled.ViewList,
                    contentDescription = stringResource(id = R.string.change_view),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}