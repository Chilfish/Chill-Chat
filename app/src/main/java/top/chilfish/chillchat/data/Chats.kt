package top.chilfish.chillchat.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import top.chilfish.chillchat.utils.formattedTime

const val Chat_Table = "chats"

@Entity(tableName = Chat_Table)
data class Chats(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val chatterId: Long,
    val lastMessage: String,
    val lastTime: Long,
) {
    @get:Ignore
    val lastTimeStr: String
        get() = formattedTime(lastTime)
}
