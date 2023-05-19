package top.chilfish.chillchat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import top.chilfish.chillchat.R
import top.chilfish.chillchat.ui.contacts.AddContactPage
import top.chilfish.chillchat.ui.contacts.ContactsPage
import top.chilfish.chillchat.ui.debugger.DebugPage
import top.chilfish.chillchat.ui.edit_profile.EditProfile
import top.chilfish.chillchat.ui.main.ChatsListPage
import top.chilfish.chillchat.ui.main.MainPage
import top.chilfish.chillchat.ui.main.MainViewModel
import top.chilfish.chillchat.ui.main.MePage
import top.chilfish.chillchat.ui.message.MessagePage
import top.chilfish.chillchat.ui.contacts.ProfilePage

const val ArgUser = "uid"
const val ArgEdit = "edit"

object Routers {
    const val Home = "home"
    const val Contact = "contact"
    const val Me = "me"

    const val Profile = "profile/{$ArgUser}"
    const val Message = "message/{$ArgUser}"

    const val EditProfile = "profile/edit/{${ArgEdit}}"

    const val AddContact = "addContact"

    const val Debug = "DebugPage"
}

object EditType {
    const val Name = "name"
    const val Avatar = "avatar"
    const val Email = "email"
    const val Password = "password"
    const val Bio = "bio"
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
        Routers.EditProfile -> Routers.EditProfile.replace("{${ArgEdit}}", data)
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