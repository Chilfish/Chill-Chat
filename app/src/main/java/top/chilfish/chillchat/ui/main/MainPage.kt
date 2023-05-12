package top.chilfish.chillchat.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import top.chilfish.chillchat.navigation.ChillNavHost
import top.chilfish.chillchat.ui.components.HomeBar
import top.chilfish.chillchat.ui.components.NavBar

@Composable
fun MainPage(
    viewModel: MainViewModel,
    navController: NavHostController,
    mainContent: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            HomeBar(viewModel)
        },
        bottomBar = {
            NavBar(navController)
        },
        content = { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                mainContent()
            }
        }
    )
}