package top.chilfish.chillchat.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.navigation.ChillNavHost
import top.chilfish.chillchat.ui.components.HomeBar
import top.chilfish.chillchat.ui.components.NavBar

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeBar(viewModel)
        },
        bottomBar = {
            NavBar(navController)
        },
        content = { innerPadding ->
            ChillNavHost(
                navController = navController,
                modifier = modifier.padding(innerPadding),
                viewModel = viewModel
            )
        }
    )
}