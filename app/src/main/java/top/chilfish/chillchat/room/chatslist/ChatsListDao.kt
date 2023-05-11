package top.chilfish.chillchat.room.chatslist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import top.chilfish.chillchat.data.Chat_Table
import top.chilfish.chillchat.data.Chats

@Dao
interface ChatsListDao {
    @Query("SELECT * FROM $Chat_Table ORDER BY lastTime DESC")
    suspend fun getAll(): MutableList<Chats>

    @Query("SELECT * FROM $Chat_Table WHERE id = :id")
    suspend fun getById(id: Long): Chats

    @Insert
    suspend fun insert(chats: Chats)

    @Query("DELETE FROM $Chat_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $Chat_Table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(chats: Chats)
}