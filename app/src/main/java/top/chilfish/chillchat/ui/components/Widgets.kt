package top.chilfish.chillchat.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import top.chilfish.chillchat.R
import top.chilfish.chillchat.provider.BaseHost


@Composable
fun VisibilityBtn(
    passwordHidden: Boolean,
    setPasswordHidden: (Boolean) -> Unit
) {
    IconButton(onClick = { setPasswordHidden(!passwordHidden) }) {
        val visibilityIcon =
            if (passwordHidden) painterResource(R.drawable.baseline_visibility_24)
            else painterResource(R.drawable.baseline_visibility_off_24)
        val description =
            if (passwordHidden) "Show password" else "Hide password"
        Icon(painter = visibilityIcon, contentDescription = description)
    }
}


@Composable
fun AvatarImg(
    modifier: Modifier = Modifier,
    url: String = BaseHost.value,
    name: String,
    contentDescription: String = stringResource(R.string.avatar),
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data("$url/$name")
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .apply(builder)
        .build()

    AsyncImage(
        model = request,
        modifier = modifier,
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.placeholder),
        error = painterResource(R.drawable.placeholder),
        onError = {
            Log.e("Chat", "img load error: $it")
        },
        alpha = alpha,
        colorFilter = colorFilter,
        alignment = alignment,
        contentScale = contentScale,
    )
}

@Composable
fun IconBtn(
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    imageVector: ImageVector,
    des: String = ""
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = des,
            modifier = Modifier.width(24.dp),
            tint = tint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Loading() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = R.drawable.loading,
                imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = Modifier.width(24.dp),
        )

        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}