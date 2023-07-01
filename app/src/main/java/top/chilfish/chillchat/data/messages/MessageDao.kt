package top.chilfish.chillchat.data.messages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.provider.curId

@Dao
interface MessageDao {
    @Transaction
    @Query(
        "SELECT * FROM $Message_Table " +
                "where (receiveId ==:chatterId AND sendId == :uid) " +
                "OR (receiveId == :uid AND sendId == :chatterId) " +
                "ORDER BY time"
    )
    fun getAll(uid: String, chatterId: String): Flow<MutableList<Message>>

    /**
     * 获取每位联系人的最新一条消息
     */
    @Transaction
    @Query(
        "SELECT * FROM $Message_Table " +
                "WHERE sendId IN (SELECT * FROM $View_ids) " +
                "OR receiveId IN (SELECT * FROM $View_ids) " +
                "GROUP BY CASE WHEN sendId < receiveId " +
                "THEN sendId || ',' || receiveId " +
                "ELSE receiveId || ',' || sendId END " +
                "HAVING time = MAX(time);"
    )
    suspend fun getLatest(): List<Message>

    @Transaction
    @Insert
    suspend fun insert(message: Message)

    @Transaction
    @Insert
    suspend fun insertAll(messages: List<Message>)

    @Transaction
    @Query("DELETE FROM $Message_Table")
    suspend fun deleteAll()

    @Transaction
    @Query("DELETE FROM $Message_Table WHERE id = :id")
    suspend fun deleteById(id: String)
}