package top.chilfish.chillchat.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.navigation.navigateTo
import top.chilfish.chillchat.ui.components.Hero
import top.chilfish.chillchat.ui.components.ProfileBar
import top.chilfish.chillchat.ui.components.ProfileBtn
import top.chilfish.chillchat.ui.components.ProfileInfo
import top.chilfish.chillchat.utils.toJson

@Composable
fun ProfilePage(
    viewModel: ProfileViewModel,
    navController: NavHostController,
) {
    val profileState = viewModel.profileState.collectAsState(initial = ProfileState()).value

    // TODO: add menu btn to delete friend
    Scaffold(
        topBar = { ProfileBar(viewModel, navController) },
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surface)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Hero(profileState.curProfile)
            ProfileInfo(profileState.curProfile)
            ProfileBtn(isMe = false) {
                navigateTo(
                    navCtrl = navController,
                    route = Routers.Message,
                    data = toJson(profileState.curProfile),
                )
            }
        }
    }
}
