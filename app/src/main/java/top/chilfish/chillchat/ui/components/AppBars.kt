package top.chilfish.chillchat.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.NavBars
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.navigation.navigateTo
import top.chilfish.chillchat.ui.main.MainViewModel
import top.chilfish.chillchat.ui.message.MessageViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors() = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)

@Composable
fun HomeBar(
    viewModel: MainViewModel,
    navHostController: NavHostController,
) {
    ChillTopBar(
        title = stringResource(R.string.app_name),
        center = false,
        actions = {
            IconBtn(
                onClick = { navHostController.navigate(Routers.Debug) },
                imageVector = Icons.Default.Build
            )
            IconBtn(
                onClick = { viewModel.search() },
                imageVector = Icons.Rounded.Search
            )
            IconBtn(
                onClick = {
                    navHostController.navigate(Routers.AddContact) {
                        popUpTo(Routers.Home) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                imageVector = Icons.Outlined.AddCircle
            )
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageBar(
    profile: Profile,
    viewModel: MessageViewModel,
    navHostController: NavHostController,
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable(
                        onClick = {
                            navigateTo(
                                navCtrl = navHostController,
                                route = Routers.Profile,
                                data = profile.id.toString(),
                            )
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = profile.avatar,
                    contentDescription = "avatar",
                    placeholder = painterResource(R.drawable.placeholder),
                    modifier = Modifier
                        .width(40.dp)
                        .clip(RoundedCornerShape(50.dp))
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = profile.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        navigationIcon = {
            IconBtn(
                onClick = { navHostController.popBackStack() },
                imageVector = Icons.Default.ArrowBack
            )
        },
        actions = {
            IconBtn(
                onClick = { viewModel.more() },
                imageVector = Icons.Rounded.MoreVert
            )
        },
        colors = appBarColors()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChillTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    center: Boolean = true,
    actions: @Composable () -> Unit = {},
    navController: NavHostController? = null,
) {
    TopAppBar(
        title = {
            Text(
                modifier = modifier.let {
                    if (center) {
                        it.fillMaxWidth()
                    } else it
                }.let {
                    if (center && navController != null) {
                        it.offset(x = (-24).dp)
                    } else it
                },
                text = title,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        },
        navigationIcon = {
            if (navController != null)
                IconBtn(
                    onClick = { navController.popBackStack() },
                    imageVector = Icons.Default.ArrowBack
                )
        },
        actions = { actions() },
        colors = appBarColors(),
    )
}


@Composable
fun NavBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
    ) {
        NavBars.forEach { navBar ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == navBar.router } == true,
                onClick = {
                    navController.navigate(navBar.router) {
                        popUpTo(Routers.Home) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(navBar.selectedIcon),
                        contentDescription = stringResource(navBar.iconTextId),
                        modifier = Modifier.height(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(navBar.iconTextId),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}
