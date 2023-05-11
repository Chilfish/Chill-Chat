package top.chilfish.chillchat.data.messages

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import top.chilfish.chillchat.utils.formattedTime

const val Message_Table = "messages"

@Entity(tableName = Message_Table)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val senderId: Long,
    val receiverId: Long,
    val message: String,
    val time: Long,
) {
    @get:Ignore
    val timeStr: String
        get() = formattedTime(time)
}
