package top.chilfish.chillchat.ui.contacts

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.ChillTopBar

@Composable
fun AddContactPage(
    viewModel: ContactsViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    ChillScaffold(
        topBar = {
            ChillTopBar(
                navController = navController,
                title = stringResource(R.string.add_contacts),
            )
        }
    )
}

