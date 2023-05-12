package top.chilfish.chillchat.ui.message

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.ui.components.MessageBar

@Composable
fun MessagePage(
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel,
) {
    val messageState = viewModel.messageState.collectAsState().value

    Scaffold(
        modifier = modifier,
        topBar = {
            MessageBar(
                profile = messageState.curProfile,
                viewModel = viewModel
            )
        }
    ) { padding ->
        MessageMain(modifier = modifier.padding(padding))
    }
}


@Composable
fun MessageMain(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {

    }
}