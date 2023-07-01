package top.chilfish.chillchat.ui.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.navigation.ArgUser
import top.chilfish.chillchat.provider.curId
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.MessageBar

private const val TimeGap = 3 * 60 * 1000

@Composable
fun MessagePage(
    viewModel: MessageViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val messageState = viewModel.messageState.collectAsState().value

    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            it.arguments?.getString(ArgUser)?.let { id ->
                viewModel.init(id)
            }
        }
    }

    ChillScaffold(
        isIme = true,
        topBar = {
            MessageBar(
                profile = messageState.chatter,
                viewModel = viewModel,
                navHostController = navController,
            )
        },
        content = {
            Column {
                MessageMain(
                    messages = messageState.messages,
                    modifier = Modifier.weight(1f),
                    scrollState = scrollState,
                )

                UserInput(
                    modifier = Modifier,
                    onSend = { message ->
                        viewModel.sendMes(message)
                    }
                )
            }
        }
    )
}

@Composable
fun MessageMain(
    modifier: Modifier = Modifier,
    messages: MutableList<Message>,
    scrollState: LazyListState,
) {
    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            scrollState.scrollToItem(messages.lastIndex)
        }
    }

    Box(modifier.background(MaterialTheme.colorScheme.tertiary)) {
        LazyColumn(
            state = scrollState,
            modifier = modifier.fillMaxSize(),
        ) {
            items(messages.size) { index ->
                val message = messages[index]

                MessageItem(
                    message = message.content,
                    time = message.timeStr,
                    isMe = message.sendId == curId,
                    isShowTime = (index == 0 || message.time - messages[index - 1].time > TimeGap)
                )
            }
        }
    }
}