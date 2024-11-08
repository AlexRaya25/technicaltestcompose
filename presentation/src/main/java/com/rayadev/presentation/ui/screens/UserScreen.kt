package com.rayadev.presentation.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rayadev.domain.model.User
import com.rayadev.presentation.R
import com.rayadev.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    onUserClick: (Int, Offset, IntSize) -> Unit) {

    val users by userViewModel.users.collectAsState()
    val currentPage by userViewModel.currentPage.collectAsState()
    var isGrid by remember { mutableStateOf(false) }

    val errorState by userViewModel.errorState.collectAsState()
    if (errorState != null) {
        Text(text = errorState ?: "Unknown error", color = Color.Red)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Usuarios", fontWeight = FontWeight.Bold)
                },
                actions = {
                    IconButton(onClick = { isGrid = !isGrid }) {
                        Icon(
                            imageVector = if (isGrid) Icons.Rounded.GridView else Icons.Filled.ViewList,
                            contentDescription = "Cambiar vista"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (users.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isGrid) {
                    items(users.chunked(2)) { rowUsers ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (user in rowUsers) {
                                UserGridItem(user, Modifier.weight(1f), onClick = {
                                    onUserClick(user.id, Offset(0f, 0f), IntSize(56, 56)) // Aquí pasas los valores correctos
                                })
                            }
                            if (rowUsers.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                } else {
                    items(users) { user ->
                        UserItem(user = user, onClick = {
                            onUserClick(user.id, Offset(0f, 0f), IntSize(56, 56)) // Aquí pasas los valores correctos
                        })
                    }
                }
                item {
                    PaginationControls(
                        currentPage = currentPage,
                        onNextPage = { userViewModel.nextPage() },
                        onPreviousPage = { userViewModel.previousPage() },
                        canGoToNextPage = userViewModel.canGoToNextPage(),
                        canGoToPreviousPage = userViewModel.canGoToPreviousPage()
                    )
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, onClick: (Int) -> Unit) {
    var transitionState = remember { MutableTransitionState(false) }
    var imagePosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var imageSize by remember { mutableStateOf(IntSize(0, 0)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                transitionState.targetState = true
                onClick(user.id)
            }
            .onGloballyPositioned { layoutCoordinates ->
                imagePosition = layoutCoordinates.positionInWindow()
                imageSize = layoutCoordinates.size
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visibleState = transitionState,
                enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(),
                exit = fadeOut(animationSpec = tween(500)) + slideOutHorizontally()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.avatar),
                    contentDescription = "Avatar de ${user.first_name} ${user.last_name}",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${user.first_name} ${user.last_name}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun UserGridItem(user: User, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatar)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "Avatar de ${user.first_name} ${user.last_name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${user.first_name} ${user.last_name}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PaginationControls(
    currentPage: Int,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    canGoToNextPage: Boolean,
    canGoToPreviousPage: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPreviousPage, enabled = canGoToPreviousPage) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Página Anterior")
        }

        Text(text = "Página $currentPage", fontWeight = FontWeight.Bold)

        IconButton(onClick = onNextPage, enabled = canGoToNextPage) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Página Siguiente")
        }
    }
}