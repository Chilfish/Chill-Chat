package top.chilfish.chillchat.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import top.chilfish.chillchat.R


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
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(R.string.avatar),
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .apply(builder)
        .build()

    AsyncImage(
        model = request,
        modifier = modifier,
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.placeholder)
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