package com.rayadev.presentation.ui.components.UserScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rayadev.domain.model.User

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UserGridItem(
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
        UserCardContent("UserGridItem", user, animatedVisibilityScope, boundsTransform)
    }
}