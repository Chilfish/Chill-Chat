package top.chilfish.chillchat.ui.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import top.chilfish.chillchat.ui.components.Alert
import top.chilfish.chillchat.ui.components.ChillTopBar
import top.chilfish.chillchat.ui.components.Hero
import top.chilfish.chillchat.ui.components.IconBtn
import top.chilfish.chillchat.ui.components.ProfileBtn
import top.chilfish.chillchat.ui.components.ProfileInfo

@Composable
fun ProfilePage(
    viewModel: ContactsViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val profileState = viewModel.contactState.collectAsState().value

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            it.arguments?.getString(ArgUser)?.let { id ->
                viewModel.loadProfile(id.toLong())
            }
        }
    }

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

@Composable
fun ProfileBar(
    viewModel: ContactsViewModel,
    navController: NavHostController,
) {
    var isAlert by rememberSaveable { mutableStateOf(false) }

    if (isAlert) {
        Alert(
            title = stringResource(R.string.alert_title_delete),
            message = stringResource(R.string.alert_message_delete),
            confirm = stringResource(R.string.confirm),
            cancel = stringResource(R.string.cancel),
            onConfirm = {
                isAlert = false
                viewModel.delContact {
                    navController.popBackStack()
                }
            },
            onCancel = { isAlert = false }
        )
    }

    ChillTopBar(
        navController = navController,
        actions = {
            IconBtn(
                onClick = { isAlert = true },
                imageVector = Icons.Rounded.Delete
            )
        }
    )
}
