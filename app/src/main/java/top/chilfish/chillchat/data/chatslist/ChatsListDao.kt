package top.chilfish.chillchat.data.chatslist

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.data.contacts.Profile

data class Chatter(
    @Embedded val chatter: Chats,
    @Relation(
        parentColumn = "chatterId",
        entityColumn = "id"
    )
    val profile: Profile
)

@Dao
interface ChatsListDao {

    @Transaction
    @Query("SELECT * FROM $Chat_Table ORDER BY lastTime DESC")
    fun getAll(): Flow<MutableList<Chatter>>

    @Query("SELECT * FROM $Chat_Table WHERE chatterId = :id")
    suspend fun getById(id: Long): Chats?

    @Insert
    suspend fun insert(chats: Chats)

    @Query("DELETE FROM $Chat_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $Chat_Table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(chats: Chats)

    @Query("UPDATE $Chat_Table SET lastMessage = :message, lastTime = :time WHERE chatterId = :chatterId")
    suspend fun updateById(chatterId: Long, message: String, time: Long)

    @Transaction
    suspend fun insertOrUpdate(chatsList: List<Chats>) {
        chatsList.forEach {
            val chatter = getById(it.chatterId)
            if (chatter == null) {
                insert(it)
            } else {
                update(it)
            }
        }
    }
}