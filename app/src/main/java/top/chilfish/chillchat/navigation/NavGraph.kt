package top.chilfish.chillchat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import top.chilfish.chillchat.ui.contacts.AddContactPage
import top.chilfish.chillchat.ui.contacts.ContactsPage
import top.chilfish.chillchat.ui.contacts.ProfilePage
import top.chilfish.chillchat.ui.debugger.DebugPage
import top.chilfish.chillchat.ui.edit_profile.EditProfile
import top.chilfish.chillchat.ui.login.LoginPage
import top.chilfish.chillchat.ui.login.LoginViewModel
import top.chilfish.chillchat.ui.main.ChatsListPage
import top.chilfish.chillchat.ui.main.MainPage
import top.chilfish.chillchat.ui.main.MainViewModel
import top.chilfish.chillchat.ui.main.MePage
import top.chilfish.chillchat.ui.message.MessagePage

@Composable
fun ChillNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Routers.Home,
    ) {
        composable(route = Routers.Home) {
            MainPage(navController = navController) {
                ChatsListPage(viewModel, navController)
            }
        }

        composable(route = Routers.Contact) {
            MainPage(navController = navController) {
                ContactsPage(
                    navController = navController
                )
            }
        }

        composable(route = Routers.Me) {
            MainPage(navController = navController) {
                MePage(viewModel, navController)
            }
        }

        composable(
            route = Routers.Profile,
            arguments = listOf(navArgument(ArgUser) {
                type = NavType.StringType
            })
        ) {
            ProfilePage(navController = navController)
        }

        composable(
            route = Routers.Message,
            arguments = listOf(navArgument(ArgUser) {
                type = NavType.StringType
            })
        ) {
            MessagePage(navController = navController)
        }

        composable(
            route = Routers.EditProfile,
            arguments = listOf(navArgument(ArgEdit) {
                type = NavType.StringType
            })
        ) {
            EditProfile(navController = navController)
        }

        composable(route = Routers.AddContact) {
            AddContactPage(
                navController = navController
            )
        }

        composable(route = Routers.Debug) {
            DebugPage()
        }
    }
}

@Composable
fun LoginNavHost(
    viewModel: LoginViewModel,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routers.Home,
    ) {
        composable(route = Routers.Home) {
            LoginPage(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(route = Routers.Debug) {
            DebugPage()
        }
    }
}