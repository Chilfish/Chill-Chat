package top.chilfish.chillchat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import top.chilfish.chillchat.R
import top.chilfish.chillchat.ui.components.HomeBar
import top.chilfish.chillchat.ui.components.NavBar
import top.chilfish.chillchat.ui.main.ChatsListPage
import top.chilfish.chillchat.ui.main.ContactsPage
import top.chilfish.chillchat.ui.main.MainPage
import top.chilfish.chillchat.ui.main.MainViewModel
import top.chilfish.chillchat.ui.main.MePage
import top.chilfish.chillchat.ui.profile.ProfilePage
import top.chilfish.chillchat.ui.profile.ProfileViewModel

const val RouteId = "Id"

object Routers {
    const val Home = "home"
    const val Contact = "contact"
    const val Me = "me"

    const val Profile = "profile/{$RouteId}"
    const val Message = "message/{$RouteId}"
}

data class NavBarDes(
    val router: String,
    @StringRes val iconTextId: Int,
    @DrawableRes val selectedIcon: Int
)

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(route: String, id: Long? = null) {
        val curRoute = when (route) {
            Routers.Home -> Routers.Home
            Routers.Contact -> Routers.Contact
            Routers.Me -> Routers.Me

            Routers.Profile -> Routers.Profile.replace("{$RouteId}", id.toString())
            Routers.Message -> Routers.Message.replace("{$RouteId}", id.toString())
            else -> route
        }

        navController.navigate(curRoute) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
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
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        NavHost(
            navController = navController,
            startDestination = Routers.Home,
        ) {
            composable(route = Routers.Home) {
                MainPage(
                    viewModel = viewModel,
                    navController = navController
                ) {
                    ChatsListPage(viewModel)
                }
            }

            composable(route = Routers.Contact) {
                MainPage(
                    viewModel = viewModel,
                    navController = navController
                ) {
                    ContactsPage(viewModel)
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
                arguments = listOf(
                    navArgument(RouteId)
                    { type = NavType.LongType }
                )
            ) {
                val id = it.arguments?.getLong(RouteId)
                val profileViewModel = remember {
                    ProfileViewModel(
                        uid = id,
                        navHostController = navController
                    )
                }
                ProfilePage(profileViewModel)
            }
        }
    }
}