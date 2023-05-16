package top.chilfish.chillchat.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.navigation.ArgUser
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.navigation.navigateTo
import top.chilfish.chillchat.ui.components.Hero
import top.chilfish.chillchat.ui.components.ProfileBar
import top.chilfish.chillchat.ui.components.ProfileBtn
import top.chilfish.chillchat.ui.components.ProfileInfo

@Composable
fun ProfilePage(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val profileState = viewModel.profileState.collectAsState(initial = ProfileState()).value

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            it.arguments?.getString(ArgUser)?.let { id ->
                viewModel.init(id.toLong())
            }
        }
    }

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

            ProfileBtn(
                isMe = false,
                text = stringResource(R.string.send_message)
            ) {
                navigateTo(
                    navCtrl = navController,
                    route = Routers.Message,
                    data = profileState.curProfile.id.toString(),
                )
            }
        }
    }
}
