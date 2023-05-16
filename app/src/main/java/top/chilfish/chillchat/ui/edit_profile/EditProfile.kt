package top.chilfish.chillchat.ui.edit_profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.ArgEdit
import top.chilfish.chillchat.ui.components.ChillScaffold

@Composable
fun EditProfile(
    viewModel: EditViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state = viewModel.editState.collectAsState().value
    val profile = remember(state.me) { state.me ?: Profile() }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            it.arguments?.getString(ArgEdit)?.let { type ->
                viewModel.init(type)
            }
        }
    }

    ChillScaffold(
        topBar = {
            EditProfileBar(
                viewModel = viewModel,
                navController = navController,
                type = state.editType,
            )
        },
        content = {
            Text(text = "$profile")
        }
    )
}
