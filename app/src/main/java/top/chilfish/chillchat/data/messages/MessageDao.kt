package top.chilfish.chillchat.data.messages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query(
        "SELECT * FROM $Message_Table " +
                "where (receiverId ==:chatterId AND senderId == :uid) " +
                "OR (receiverId == :uid AND senderId == :chatterId) " +
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