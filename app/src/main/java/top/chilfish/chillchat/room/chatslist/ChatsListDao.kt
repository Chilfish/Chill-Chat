package top.chilfish.chillchat.room.chatslist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.data.Chat_Table
import top.chilfish.chillchat.data.ChatsList

@Dao
interface ChatsListDao {
    @Query("SELECT * FROM $Chat_Table ORDER BY lastTime DESC")
    fun getAll(): Flow<MutableList<ChatsList>>

    @Query("SELECT * FROM $Chat_Table WHERE id = :id")
    suspend fun getById(id: Long): ChatsList

    @Insert
    suspend fun insert(chatsList: ChatsList)

    @Query("DELETE FROM $Chat_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $Chat_Table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(chatsList: ChatsList)
}