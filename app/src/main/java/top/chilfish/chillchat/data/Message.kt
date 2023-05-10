package top.chilfish.chillchat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

const val Message_Table = "messages"

@Entity(tableName = Message_Table)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val senderId: Long,
    val receiverId: Long,
    val message: String,
    val time: Long,
)
