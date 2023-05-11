package top.chilfish.chillchat.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

const val User_Table = "users"

@Entity(tableName = User_Table)
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "Default Name",
    val avatar: String = "https://p.chilfish.top/avatar.webp",
    val email: String = "Default Email",
    val bio: String = "Default Bio",
)
