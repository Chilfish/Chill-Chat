package top.chilfish.chillchat.data.messages

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import top.chilfish.chillchat.utils.formattedTime

const val Message_Table = "messages"

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
