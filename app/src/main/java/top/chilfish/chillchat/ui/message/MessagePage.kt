package top.chilfish.chillchat.ui.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.provider.curUid
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.MessageBar

private const val TimeGap = 3 * 60 * 1000

@Composable
fun MessagePage(
    viewModel: MessageViewModel,
) {
    val messageState = viewModel.messageState.collectAsState().value

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    ChillScaffold(
        isIme = true,
        topBar = {
            MessageBar(
                profile = messageState.curProfile,
                viewModel = viewModel
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
                    resetScroll = {
                        scope.launch {
                            scrollState.scrollToItem(0)
                        }
                    },
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
    Box(modifier.background(MaterialTheme.colorScheme.tertiary)) {
//        Image(
//            painter = painterResource(R.drawable.chat_bg_light),
//            contentDescription = "background",
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier.fillMaxSize(),
//        )

        LazyColumn(
            state = scrollState,
            modifier = modifier.fillMaxSize(),
        ) {
            items(messages.size) { index ->
                val message = messages[index]

                MessageItem(
                    message = message.message,
                    time = message.timeStr,
                    isMe = message.senderId == curUid,
                    isShowTime = (index == 0 || message.time - messages[index - 1].time > TimeGap)
                )
            }
        }
    }
}