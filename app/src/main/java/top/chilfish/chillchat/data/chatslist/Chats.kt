package top.chilfish.chillchat.data.chatslist

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import top.chilfish.chillchat.utils.formattedTime

const val Chat_Table = "chats"

// 聊天列表
@Entity(tableName = Chat_Table)
data class Chats(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chatterId: String = "",
    val lastMessage: String = "",
    val lastTime: Long = 0,
) {
    @get:Ignore
    val lastTimeStr: String
        get() = formattedTime(lastTime)
}
