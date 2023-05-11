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
import androidx.compose.ui.unit.dp
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.ui.components.Hero
import top.chilfish.chillchat.ui.components.ProfileBtn
import top.chilfish.chillchat.ui.components.ProfileInfo

@Composable
fun MePage(
    viewModel: MainViewModel
) {
    val profile = viewModel.mainState
        .collectAsState(initial = MainState())
        .value.me ?: Profile()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Hero(profile)
        ProfileInfo(profile)
        ProfileBtn(isMe = true) { viewModel.logout() }
    }
}