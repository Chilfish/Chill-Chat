package top.chilfish.chillchat.data.contacts

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

const val User_Table = "users"
const val Host = "chilfish.top"
const val AvatarHost = "https://p.$Host"

@Entity(tableName = User_Table)
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "Default Name",
    val avatar: String = "$AvatarHost/avatar0.webp",
    val email: String = "Default Email@$Host",
    val bio: String = "Default Bio",
)
