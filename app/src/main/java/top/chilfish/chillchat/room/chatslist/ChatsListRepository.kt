package top.chilfish.chillchat.room.chatslist

import top.chilfish.chillchat.data.Chats

class ChatsListRepository(private val dao: ChatsListDao) {
    suspend fun allChats() = dao.getAll()

    suspend fun insert(chats: Chats) = dao.insert(chats)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(chats: Chats) = dao.update(chats)

    suspend fun getById(id: Long) = dao.getById(id)
}