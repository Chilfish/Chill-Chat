package top.chilfish.chillchat.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.EditType
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.navigation.navigateTo
import top.chilfish.chillchat.ui.components.Hero
import top.chilfish.chillchat.ui.components.PickFile
import top.chilfish.chillchat.ui.components.ProfileBtn
import top.chilfish.chillchat.ui.components.ProfileInfo

@Composable
fun MePage(
    viewModel: MainViewModel,
    navController: NavHostController,
) {
    val profile = viewModel.mainState.collectAsState()
        .value.me ?: Profile()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PickFile(pickedFile = { viewModel.changeAvatar(it) }) { launcher ->
            Hero(
                profile = profile,
                isMe = true,
                navController = navController,
                changeAvatar = { launcher() }
            )
        }

        ProfileInfo(
            profile = profile,
            isMe = true,
            navController = navController
        )

        ProfileBtn(
            text = stringResource(R.string.reset_password),
        ) {
            navigateTo(
                navController,
                Routers.EditProfile,
                EditType.Password
            )
        }

        ProfileBtn(
            isMe = true,
            text = stringResource(R.string.logout),
            onClick = { viewModel.logout() }
        )
    }
}