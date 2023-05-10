package top.chilfish.chillchat.room.messages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.data.Message
import top.chilfish.chillchat.data.Message_Table

@Dao
interface MessageDao {
    @Query(
        "SELECT * FROM $Message_Table " +
                "where (receiverId == :uid || senderId == :uid) " +
                "AND (receiverId == :chatterId || senderId == :chatterId) " +
                "ORDER BY time DESC"
    )
    fun getAll(uid: Long, chatterId: Long): Flow<MutableList<Message>>

    @Insert
    suspend fun insert(message: Message)

    @Query("DELETE FROM $Message_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $Message_Table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM $Message_Table WHERE senderId = :chatterId OR receiverId = :chatterId")
    suspend fun deleteByChatterId(chatterId: Long)
}