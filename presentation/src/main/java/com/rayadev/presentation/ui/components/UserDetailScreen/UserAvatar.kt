package com.rayadev.presentation.ui.components.userDetailScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UserAvatar(
    avatarUrl: String,
    userId: Int,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    Image(
        painter = rememberAsyncImagePainter(avatarUrl),
        contentDescription = null,
        modifier = Modifier
            .size(120.dp)
            .sharedElement(
                rememberSharedContentState(key = "image-$userId"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform
            )
            .clip(CircleShape)
            .background(Color.Transparent),
        contentScale = ContentScale.Crop
    )
}