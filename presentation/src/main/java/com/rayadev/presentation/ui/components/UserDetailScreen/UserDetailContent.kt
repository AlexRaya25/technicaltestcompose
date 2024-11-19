package com.rayadev.presentation.ui.components.UserDetailScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rayadev.domain.model.User

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UserDetailContent(
    user: User,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .sharedElement(
                rememberSharedContentState(key = "card-${user.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            UserAvatar(
                avatarUrl = user.avatar,
                userId = user.id,
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = boundsTransform
            )
            Spacer(modifier = Modifier.height(16.dp))
            UserBasicInfo(user)
            DividerSection()
            UserDetailsSection()
            DividerSection()
            UserAboutSection(user)
        }
    }
}
