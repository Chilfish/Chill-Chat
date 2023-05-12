package top.chilfish.chillchat.data.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import top.chilfish.chillchat.utils.toJson
import java.net.URL
import java.net.URLEncoder

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
