package top.chilfish.chillchat.data.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import top.chilfish.chillchat.provider.BaseHost

const val User_Table = "users"
val Host = BaseHost.value

// 用户/联系人 信息表
@Serializable
@Entity(tableName = User_Table)
data class Profile(
    @PrimaryKey
    val id: String = "",
    val cid: String = "default_name",

    val nickname: String = "Default Name",
    val email: String = "Default Email@$Host",
    val bio: String = "Default Bio",
    val avatar: String = "avatar0.webp",
)
