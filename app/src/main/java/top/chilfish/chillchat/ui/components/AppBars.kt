package top.chilfish.chillchat.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.search),
                    modifier = Modifier.width(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        navigationIcon = {},
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageBar(
    profile: Profile,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClick) {
                    AsyncImage(
                        model = profile.avatar,
                        contentDescription = "avatar",
                        placeholder = painterResource(R.drawable.placeholder),
                        modifier = Modifier
                            .width(40.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = profile.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .width(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier.width(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
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