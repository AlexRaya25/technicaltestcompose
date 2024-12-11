package com.rayadev.presentation.ui.components.userScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.rayadev.domain.model.User
import com.rayadev.presentation.R
import okhttp3.OkHttpClient
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.UserCardContent(
    typeItem: String,
    user: User,
    animatedVisibilityScope: AnimatedVisibilityScope,
    boundsTransform: BoundsTransform
) {

    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(
            p0: Array<out java.security.cert.X509Certificate>?,
            authType: String?
        ) {}
        override fun checkServerTrusted(
            p0: Array<out java.security.cert.X509Certificate>?,
            authType: String?
        ) {}
        override fun getAcceptedIssuers(): Array<out java.security.cert.X509Certificate>? = arrayOf()
    })

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .okHttpClient {
            OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .build()
        }
        .crossfade(true)
        .build()


    if (typeItem == "UserGridItem") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = user.avatar,
                    imageLoader = imageLoader
                ),
                modifier = Modifier
                    .size(80.dp)
                    .sharedElement(
                        rememberSharedContentState(key = "image-${user.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.avatar)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = user.first_name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = user.last_name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
        }
    } else {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .sharedElement(
                        rememberSharedContentState(key = "image-${user.id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    model = user.avatar,
                    imageLoader = imageLoader
                ),
                contentDescription = stringResource(id = R.string.avatar)
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
}