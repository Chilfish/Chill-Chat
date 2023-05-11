package top.chilfish.chillchat.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.chilfish.chillchat.data.chatslist.Chats
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.ui.components.AvatarImg

@Composable
fun ColumnScope.ChatsListPage(
    viewModel: MainViewModel,
) {
    val chats = viewModel.mainState.collectAsState(initial = MainState()).value.chats

    LazyColumn(Modifier.weight(1f)) {
        itemsIndexed(
            items = chats,
            key = { _, chat -> chat.id }) { _, chat ->
            val profile = viewModel.getChatProfile(chat.chatterId)
            ChatsListItem(
                chat = chat,
                profile = profile,
                onClick = { viewModel.navToMessage(chat) }
            )
        }
    }
}

@Composable
fun ChatsListItem(
    chat: Chats,
    profile: Profile,
    onClick: () -> Unit
) {
    val padding = 12.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(padding),
    ) {
        AvatarImg(
            url = profile.avatar,
            modifier = Modifier
                .width(56.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Column(
            modifier = Modifier
                .padding(horizontal = padding, vertical = padding / 2)
                .weight(1f)
        ) {
            Text(
                text = profile.name,
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 18.sp,
            )
            Text(
                text = chat.lastMessage,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1,
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = chat.lastTimeStr,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}
