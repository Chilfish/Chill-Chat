package top.chilfish.chillchat.ui.contacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.navigation.navigateTo
import top.chilfish.chillchat.ui.components.AvatarImg

@Composable
fun ContactsPage(
    viewModel: ContactsViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val contacts = viewModel.contactState.collectAsState().value.contacts

    LazyColumn {
        itemsIndexed(items = contacts, key = { _, profile -> profile.id }
        ) { _, profile ->
            ContactItem(profile = profile,
                onClick = {
                    navigateTo(
                        navCtrl = navController,
                        route = Routers.Profile,
                        data = profile.id,
                    )
                }
            )
        }
    }
}


private val padding = 12.dp

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    profile: Profile,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarImg(
            name = profile.avatar,
            modifier = Modifier
                .width(56.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Column(
            modifier = Modifier
                .padding(horizontal = padding, vertical = padding / 2)
                .weight(1f)
        ) {
            Text(
                text = profile.nickname,
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 18.sp,
            )
        }
    }
}