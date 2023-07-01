package top.chilfish.chillchat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.EditType
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.navigation.navigateTo

private fun Modifier.clickToEdit(
    navController: NavHostController?,
    isMe: Boolean,
    type: String,
): Modifier {
    return if (isMe && navController != null)
        clickable {
            navigateTo(navController, Routers.EditProfile, type)
        }
    else this
}


@Composable
fun Hero(
    profile: Profile,
    isMe: Boolean = false,
    navController: NavHostController? = null,
    changeAvatar: () -> Unit = {},
) {
    Row(
        Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth()
    ) {
        AvatarImg(
            name = profile.avatar,
            modifier = Modifier
                .let { if (isMe) it.clickable { changeAvatar() } else it }
        )
        Column(Modifier.padding(24.dp)) {
            Row(
                Modifier.clickToEdit(navController, isMe, EditType.Name)
            ) {
                Text(
                    text = profile.nickname,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 12.dp),
                )
                if (isMe)
                    Icon(
                        Icons.Outlined.Edit,
                        null,
                        Modifier
                            .width(18.dp)
                            .padding(start = 4.dp),
                        tint = MaterialTheme.colorScheme.surfaceTint,
                    )
            }
            Text(text = stringResource(R.string.cid, profile.cid))
        }
    }
}

@Composable
fun ProfileInfo(
    profile: Profile,
    isMe: Boolean = false,
    navController: NavHostController? = null,
) {
    Card(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .shadow(2.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.account),
                modifier = Modifier.padding(12.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            ProfileFrag(
                title = stringResource(R.string.email),
                subtitle = profile.email,
                modifier = Modifier.clickToEdit(navController, isMe, EditType.Email),
                isMe = isMe,
            )
            ProfileFrag(
                title = stringResource(R.string.bio),
                subtitle = profile.bio,
                modifier = Modifier.clickToEdit(navController, isMe, EditType.Bio),
                isMe = isMe,
            )
        }
    }
}

@Composable
fun ProfileFrag(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    isMe: Boolean,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = subtitle)
            Text(
                text = title,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                color = Color.Gray
            )
        }
        if (isMe)
            Icon(
                Icons.Outlined.KeyboardArrowRight,
                null, Modifier.padding(start = 4.dp),
                tint = MaterialTheme.colorScheme.surfaceTint
            )
    }
}

@Composable
fun ProfileBtn(
    isMe: Boolean = false,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        colors = ButtonDefaults.buttonColors(
            if (isMe) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(12.dp),
            fontWeight = FontWeight.Bold,
        )
    }
}