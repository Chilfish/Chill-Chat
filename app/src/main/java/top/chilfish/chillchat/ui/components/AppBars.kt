package top.chilfish.chillchat.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.NavBars
import top.chilfish.chillchat.navigation.NavigationActions
import top.chilfish.chillchat.ui.main.MainViewModel
import top.chilfish.chillchat.ui.message.MessageViewModel
import top.chilfish.chillchat.ui.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBarColors() = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBar(
    viewModel: MainViewModel,
    title: String = stringResource(R.string.app_name),
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconBtn(
                onClick = { viewModel.search() },
                imageVector = Icons.Rounded.Search
            )
            IconBtn(
                onClick = { viewModel.addFriend() },
                imageVector = Icons.Outlined.AddCircle
            )
        },
        navigationIcon = {},
        colors = appBarColors(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageBar(
    profile: Profile,
    viewModel: MessageViewModel
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable(
                        onClick = { viewModel.navToProfile() }
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
                onClick = { viewModel.back() },
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
fun ProfileBar(
    viewModel: ProfileViewModel,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconBtn(
                onClick = { viewModel.back() },
                imageVector = Icons.Default.ArrowBack
            )
        },
        actions = {
            IconBtn(
                onClick = { viewModel.more() },
                imageVector = Icons.Rounded.MoreVert
            )
        },
        colors = appBarColors(),
    )
}

@Composable
fun NavBar(
    navController: NavHostController,
) {
    val (selectedItem, setSelectedItem) = rememberSaveable { mutableStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
    ) {
        NavBars.forEachIndexed { index, navBar ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    setSelectedItem(index)
                    NavigationActions(navController).navigateTo(navBar.router)
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
