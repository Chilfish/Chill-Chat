package top.chilfish.chillchat.data.chatslist

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ChatsListRepository @Inject constructor(
    private val dao: ChatsListDao
) {
    suspend fun getAll() = dao.getAll()

    suspend fun insert(chats: Chats) = dao.insert(chats)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(chats: Chats) = dao.update(chats)

    suspend fun updateById(chatterId: Long, message: String, time: Long) =
        dao.updateById(chatterId, message, time)
}