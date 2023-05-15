package top.chilfish.chillchat.ui.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.EditProfileBar

@Composable
fun EditProfile(
    viewModel: MainViewModel,
    navController: NavHostController,
    type: String,
) {
    val state = viewModel.mainState.collectAsState()
    val profile = remember(state.value.me) { state.value.me ?: Profile() }

    ChillScaffold(
        topBar = {
            EditProfileBar(
                viewModel = viewModel,
                navController = navController,
                type = type,
                profile = profile,
            )
        },
        content = {
            Text(text = "$profile")
        }
    )
}
