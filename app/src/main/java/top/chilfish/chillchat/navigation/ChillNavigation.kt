package top.chilfish.chillchat.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R

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
