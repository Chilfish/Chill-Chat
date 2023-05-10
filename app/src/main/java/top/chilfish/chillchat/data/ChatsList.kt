package top.chilfish.chillchat.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

const val Chat_Table = "chats"

@Entity(tableName = Chat_Table)
data class ChatsList(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val chatterId: Long,
    val lastMessage: String,
    val lastTime: Long,
) {
    @get:Ignore
    val chatter: Profile?
        get() {
            // TODO:  get chatter from database
            return null
        }
}
