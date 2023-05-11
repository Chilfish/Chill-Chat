package top.chilfish.chillchat.data.chatslist


class ChatsListRepository(private val dao: ChatsListDao) {
    suspend fun allChats() = dao.getAll()

    suspend fun insert(chats: Chats) = dao.insert(chats)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(chats: Chats) = dao.update(chats)

    suspend fun getById(id: Long) = dao.getById(id)
}