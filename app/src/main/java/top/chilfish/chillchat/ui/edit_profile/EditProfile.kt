package top.chilfish.chillchat.ui.edit_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.ArgEdit
import top.chilfish.chillchat.navigation.EditType
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.TextInput

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
                viewModel.init(type, navController)
            }
        }
    }

    var text by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }

    ChillScaffold(
        topBar = {
            EditProfileBar(
                navController = navController,
                type = state.editType,
                onSave = { viewModel.saveEdit(text, newPassword) },
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (state.editType) {
                    EditType.Name, EditType.Bio, EditType.Email, EditType.Password -> {
                        EditInput(
                            type = state.editType,
                            text = inputText(state.editType, profile),
                            newPassword = newPassword,
                            onTextChange = { text = it },
                            onNewPasswordChange = { newPassword = it },
                        )
                    }

                    else -> {}
                }
            }
        }
    )
}

private val inputText = { type: String, profile: Profile ->
    when (type) {
        EditType.Name -> profile.name
        EditType.Email -> profile.email
        EditType.Bio -> profile.bio
        else -> ""
    }
}

@Composable
private fun EditInput(
    type: String,
    text: String,
    newPassword: String,
    onTextChange: (String) -> Unit = {},
    onNewPasswordChange: (String) -> Unit = {},
) {
    TextInput(
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        label = type,
        isPassword = type == EditType.Password,
    )
    if (type == EditType.Password) {
        TextInput(
            label = stringResource(R.string.new_password),
            value = newPassword,
            onValueChange = { onNewPasswordChange(it) },
            isPassword = true,
        )
    }
}