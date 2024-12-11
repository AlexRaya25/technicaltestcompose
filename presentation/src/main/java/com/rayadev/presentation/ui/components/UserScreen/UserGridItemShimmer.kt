package com.rayadev.presentation.ui.components.userScreen

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun UserGridItemShimmer(
    highlightColor: Color = Color.Gray.copy(alpha = 0.3f),
    animationSpec: InfiniteRepeatableSpec<Float> = InfiniteRepeatableSpec(
        animation = tween(durationMillis = 1000),
        repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
    ),
    progressForMaxAlpha: Float = 0.7f
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = highlightColor,
                                animationSpec = animationSpec,
                                progressForMaxAlpha = progressForMaxAlpha
                            )
                        )
                        .background(MaterialTheme.colorScheme.surface)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(16.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = highlightColor,
                                animationSpec = animationSpec,
                                progressForMaxAlpha = progressForMaxAlpha
                            )
                        )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(12.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = highlightColor,
                                animationSpec = animationSpec,
                                progressForMaxAlpha = progressForMaxAlpha
                            )
                        )
                )
            }
        }
    }
}