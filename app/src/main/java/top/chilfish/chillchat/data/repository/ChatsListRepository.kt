package top.chilfish.chillchat.data.repository

import top.chilfish.chillchat.data.chatslist.Chats
import top.chilfish.chillchat.data.chatslist.ChatsListDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatsListRepository @Inject constructor(
    private val dao: ChatsListDao,
    private val api: ApiRequest,
) {
    fun getAll() = dao.getAll()

    suspend fun insert(chats: Chats) = dao.insert(chats)

    suspend fun delete(id: String) = dao.deleteById(id)

    suspend fun update(chats: Chats) = dao.update(chats)

    suspend fun updateById(chatterId: String, message: String, time: Long) {
        val res = dao.getById(chatterId)
        if (res == null) {
            dao.insert(
                Chats(
                    chatterId = chatterId,
                    lastMessage = message,
                    lastTime = time
                )
            )
        } else {
            dao.updateById(chatterId, message, time)
        }
    }

    suspend fun loadAll() {
//        withApiService { apiService ->
//            val res = apiService.loadChats(curUid)
//            Log.d("Chat", "repo: all chats: ${res}")
//            dao.deleteAll()
//            res.forEach { dao.insert(it) }
//        }
    }
}
