package top.chilfish.chillchat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import top.chilfish.chillchat.R
import top.chilfish.chillchat.ui.main.ChatsListPage
import top.chilfish.chillchat.ui.main.ContactsPage
import top.chilfish.chillchat.ui.main.MainViewModel
import top.chilfish.chillchat.ui.main.MePage

object Routers {
    const val Home = "home"
    const val Contact = "contact"
    const val Me = "me"
}

data class NavBarDes(
    val router: String,
    @StringRes val iconTextId: Int,
    @DrawableRes val selectedIcon: Int
)

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(route: String) {
        navController.navigate(route) {
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
                ChatsListPage(viewModel)
            }

            composable(route = Routers.Contact) {
                ContactsPage(viewModel)
            }

            composable(route = Routers.Me) {
                MePage(viewModel)
            }
        }
    }
}