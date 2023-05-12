package top.chilfish.chillchat.ui.message

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import top.chilfish.chillchat.R
import top.chilfish.chillchat.provider.curUid
import top.chilfish.chillchat.ui.components.MessageBar
import top.chilfish.chillchat.ui.components.MessageItem

private const val TimeGap = 3 * 60 * 1000

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
        MessageMain(
            modifier = modifier.padding(padding),
            viewModel = viewModel
        )
    }
}


@Composable
fun MessageMain(
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel,
) {
    val messages = viewModel.messageState.collectAsState().value.messages

    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.chat_bg_light),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )

        LazyColumn {
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