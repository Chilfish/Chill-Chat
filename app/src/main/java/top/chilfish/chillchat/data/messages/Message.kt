package top.chilfish.chillchat.data.messages

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import top.chilfish.chillchat.utils.formattedTime

const val Message_Table = "messages"
const val View_ids = "view_ids"

@Serializable
@Entity(tableName = Message_Table)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sendId: String,
    val receiveId: String,
    val content: String,
    val time: Long = System.currentTimeMillis(),
) {
    @get:Ignore
    val timeStr: String
        get() = formattedTime(time)
}

@DatabaseView(
    value = "SELECT sendId as ids FROM messages " +
            "UNION " +
            "SELECT receiveId FROM messages",
    viewName = View_ids
)
data class Ids(
    val sendId: String,
    val receiveId: String,
)

@Serializable
data class MessagesItem(
    val chatterId: String,
    val messages: List<Message>,
)