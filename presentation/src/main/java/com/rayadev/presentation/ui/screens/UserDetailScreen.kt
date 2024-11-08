package com.rayadev.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rayadev.presentation.R
import com.rayadev.presentation.viewmodel.UserViewModel
import kotlin.math.roundToInt
import androidx.compose.foundation.layout.Column as Column

@Composable
fun UserDetailScreen(userId: Int, imagePosition: Offset, imageSize: IntSize, userViewModel: UserViewModel = hiltViewModel()) {
    val user by userViewModel.getUserById(userId).collectAsState(initial = null)

    val targetOffset = remember { Animatable(imagePosition) }
    val targetWidth = remember { Animatable(imageSize.width.toFloat()) }
    val targetHeight = remember { Animatable(imageSize.height.toFloat()) }

    LaunchedEffect(userId) {
        targetOffset.animateTo(Offset(0f, 0f), animationSpec = tween(800))

        targetWidth.animateTo(150f, animationSpec = tween(800))
        targetHeight.animateTo(150f, animationSpec = tween(800))
    }

    // Asegúrate de que la imagen solo se dibuje cuando el usuario esté disponible
    AnimatedVisibility(visible = user != null) {
        user?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it.avatar),
                    contentDescription = "Avatar de ${it.first_name} ${it.last_name}",
                    modifier = Modifier
                        .offset { IntOffset(targetOffset.value.x.roundToInt(), targetOffset.value.y.roundToInt()) }
                        .size(targetWidth.value.dp, targetHeight.value.dp) // Usando los valores animados
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .animateEnterExit(
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500))
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${it.first_name} ${it.last_name}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it.email,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}