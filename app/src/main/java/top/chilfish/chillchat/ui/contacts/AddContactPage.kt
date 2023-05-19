package top.chilfish.chillchat.ui.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.ChillTopBar

@Composable
fun AddContactPage(
    viewModel: ContactsViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    ChillScaffold(
        topBar = {
            ChillTopBar(
                navController = navController,
                title = stringResource(R.string.add_contacts),
            )
        },
        content = {
            SearchRes(viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchRes(
    viewModel: ContactsViewModel,
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    val res = viewModel.contactState.collectAsState().value.searchRes

    Column {
        SearchBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            query = text,
            onQueryChange = { if (it.isDigitsOnly()) text = it },
            onSearch = {
                active = false
                if (text.isDigitsOnly() && text != "")
                    viewModel.search(text.toLong())
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(stringResource(R.string.add_contacts_hint)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        ) {}

        // Results
        LazyColumn(
            modifier = Modifier.padding(16.dp),
        ) {
            itemsIndexed(items = res) { _, user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ContactItem(
                        profile = user,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        modifier = Modifier
                            .width(64.dp)
                            .height(36.dp),
                        onClick = { viewModel.addContact(user) },
                        content = { Text(stringResource(R.string.add)) },
                    )
                }
            }
        }
    }
}
