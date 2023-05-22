package top.chilfish.chillchat.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.HomeBar
import top.chilfish.chillchat.ui.components.NavBar

@Composable
fun MainPage(
    viewModel: MainViewModel,
    navController: NavHostController,
    mainContent: @Composable () -> Unit = {}
) {
    ChillScaffold(
        topBar = { HomeBar(viewModel, navController) },
        bottomBar = { NavBar(navController) },
        content = { mainContent() }
    )
}