package top.chilfish.chillchat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import top.chilfish.chillchat.R
import top.chilfish.chillchat.ui.main.ChatsListPage
import top.chilfish.chillchat.ui.main.ContactsPage
import top.chilfish.chillchat.ui.main.MainPage
import top.chilfish.chillchat.ui.main.MainViewModel
import top.chilfish.chillchat.ui.main.MePage
import top.chilfish.chillchat.ui.message.MessagePage
import top.chilfish.chillchat.ui.message.MessageViewModel
import top.chilfish.chillchat.ui.profile.ProfilePage
import top.chilfish.chillchat.ui.profile.ProfileViewModel
import top.chilfish.chillchat.utils.toData

const val ArgUser = "uid"

object Routers {
    const val Home = "home"
    const val Contact = "contact"
    const val Me = "me"

    const val Profile = "profile/{$ArgUser}"
    const val Message = "message/{$ArgUser}"
}

data class NavBarDes(
    val router: String,
    @StringRes val iconTextId: Int,
    @DrawableRes val selectedIcon: Int
)


fun navigateTo(navCtrl: NavHostController, route: String, data: String = "") {
    val curRoute = when (route) {
        Routers.Profile -> Routers.Profile.replace("{$ArgUser}", data)
        Routers.Message -> Routers.Message.replace("{$ArgUser}", data)
        else -> route
    }

    navCtrl.navigate(curRoute) {
        launchSingleTop = true
        restoreState = true
    }
}


val NavBars = listOf(
    NavBarDes(
        router = Routers.Home,
        iconTextId = R.string.home,
        selectedIcon = R.drawable.outline_chat_24
    ),
    NavBarDes(
        router = Routers.Contact,
        iconTextId = R.string.contacts,
        selectedIcon = R.drawable.contact
    ),
    NavBarDes(
        router = Routers.Me,
        iconTextId = R.string.profile,
        selectedIcon = R.drawable.outline_person_24
    )
)


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
            MainPage(
                viewModel = viewModel,
                navController = navController
            ) {
                ChatsListPage(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }

        composable(route = Routers.Contact) {
            MainPage(
                viewModel = viewModel,
                navController = navController
            ) {
                ContactsPage(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }

        composable(route = Routers.Me) {
            MainPage(
                viewModel = viewModel,
                navController = navController
            ) {
                MePage(viewModel)
            }
        }

        composable(
            route = Routers.Profile,
            arguments = listOf(navArgument(ArgUser) {
                type = NavType.StringType
            })
        ) {
            val profile = it.arguments?.getString(ArgUser)

            val profileViewModel = remember {
                ProfileViewModel(toData(profile))
            }
            ProfilePage(
                viewModel = profileViewModel,
                navController = navController
            )
        }

        composable(
            route = Routers.Message,
            arguments = listOf(navArgument(ArgUser) {
                type = NavType.StringType
            })
        ) {
            val profile = it.arguments?.getString(ArgUser)

            val messageViewModel = remember {
                MessageViewModel(toData(profile))
            }

            MessagePage(
                viewModel = messageViewModel,
                navController = navController,
            )
        }
    }

}