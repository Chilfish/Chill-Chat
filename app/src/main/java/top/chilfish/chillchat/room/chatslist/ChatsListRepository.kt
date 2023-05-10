package top.chilfish.chillchat.room.chatslist

import top.chilfish.chillchat.data.ChatsList

class ChatsListRepository(private val dao: ChatsListDao) {
    val allChats = dao.getAll()

    suspend fun insert(chatsList: ChatsList) = dao.insert(chatsList)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(chatsList: ChatsList) = dao.update(chatsList)

    suspend fun getById(id: Long) = dao.getById(id)
}