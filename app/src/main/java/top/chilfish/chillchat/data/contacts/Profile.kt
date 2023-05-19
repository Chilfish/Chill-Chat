package top.chilfish.chillchat.data.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import top.chilfish.chillchat.provider.BaseHost

const val User_Table = "users"
val Host = BaseHost.value

@Entity(tableName = User_Table)
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = "Default Name",
    val avatar: String = "avatar0.webp",
    val email: String = "Default Email@$Host",
    val bio: String = "Default Bio",
)
