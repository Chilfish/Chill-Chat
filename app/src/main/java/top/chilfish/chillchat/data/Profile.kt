package top.chilfish.chillchat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

const val User_Table = "users"

@Entity(tableName = User_Table)
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val avatar: String,
    val email: String,
    val bio: String,
)
