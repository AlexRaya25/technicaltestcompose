package com.rayadev.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rayadev.domain.model.User
import com.rayadev.presentation.viewmodel.UserViewModel


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    val users by userViewModel.users.collectAsState()
    val currentPage by userViewModel.currentPage.collectAsState()
    val isGridView by userViewModel.isGridView.collectAsState()

    Scaffold(
        topBar = { UserTopAppBar(isGridView, userViewModel) },
        content = { paddingValues ->
            UserContent(
                users = users,
                currentPage = currentPage,
                isGridView = isGridView,
                onUserClick = onUserClick,
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform,
                paddingValues = paddingValues,
                userViewModel = userViewModel
            )
        }
    )
}

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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserContent(
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
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            UserListOrGridView(
                users = users,
                isGridView = isGridView,
                onUserClick = onUserClick,
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform
            )
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

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserListOrGridView(
    users: List<User>,
    isGridView: Boolean,
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    if (isGridView) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f).fillMaxSize().padding(horizontal = 8.dp)
        ) {
            items(users) { user ->
                UserGridItem(
                    user = user,
                    onUserClick = onUserClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
            }
        }
    } else {
        LazyColumn(modifier = Modifier.weight(1f).fillMaxSize()) {
            items(users) { user ->
                UserListItem(
                    user = user,
                    onUserClick = onUserClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserListItem(
    user: User,
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onUserClick(user.id) }
            .sharedElement(
                rememberSharedContentState(key = "card-${user.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        UserCardContent(user, animatedVisibilityScope, boundsTransform)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserGridItem(
    user: User,
    onUserClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .clickable { onUserClick(user.id) }
            .sharedElement(
                rememberSharedContentState(key = "card-${user.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        UserCardContent(user, animatedVisibilityScope, boundsTransform)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserCardContent(
    user: User,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    Row(modifier = Modifier.padding(16.dp)) {
        Image(
            modifier = Modifier
                .size(56.dp)
                .background(Color.Transparent)
                .clip(CircleShape)
                .sharedElement(
                    rememberSharedContentState(key = "image-${user.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = boundsTransform
                ),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(user.avatar),
            contentDescription = "Avatar"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "${user.first_name} ${user.last_name}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
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
            .background(Color.Transparent)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onPreviousPage,
            enabled = canGoToPreviousPage,
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Página Anterior",
                tint = if (canGoToPreviousPage) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

        Text(
            text = "Página $currentPage",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            onClick = onNextPage,
            enabled = canGoToNextPage,
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Página Siguiente",
                tint = if (canGoToNextPage) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
