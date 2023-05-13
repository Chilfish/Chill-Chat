package top.chilfish.chillchat.ui.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.provider.curUid
import top.chilfish.chillchat.ui.components.MessageBar

private const val TimeGap = 3 * 60 * 1000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagePage(
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel,
) {
    val messageState = viewModel.messageState.collectAsState().value

    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MessageBar(
                profile = messageState.curProfile,
                viewModel = viewModel
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            MessageMain(
                messages = messageState.messages,
                modifier = Modifier.weight(1f),
                scrollState = scrollState,
            )

            UserInput(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
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
}

@Composable
fun MessageMain(
    modifier: Modifier = Modifier,
    messages: MutableList<Message>,
    scrollState: LazyListState,
) {
    Box(modifier) {
        Image(
            painter = painterResource(R.drawable.chat_bg_light),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )

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
                    isShowTime = (index == 0 || messages[index - 1].time - message.time > TimeGap)
                )
            }
        }
    }
}