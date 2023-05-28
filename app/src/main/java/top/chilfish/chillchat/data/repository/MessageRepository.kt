package top.chilfish.chillchat.data.repository

import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.messages.MessageDao
import top.chilfish.chillchat.provider.curCid
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val dao: MessageDao
) {

    fun getAll(chatterId: String) = dao.getAll(curCid, chatterId)

    suspend fun insert(message: Message) = dao.insert(message)

    suspend fun delete(id: String) = dao.deleteById(id)

    suspend fun deleteByChatterId(chatterId: String) = dao.deleteByChatterId(chatterId)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun loadAll() {
//        withApiService { apiMessage ->
//            val res = apiMessage.loadMessage(curUid)
//            Log.d("Chat", "repo: all mes: ${res.size}")
//            dao.deleteAll()
//            res.forEach { dao.insert(it) }
//        }
    }

    // TODO SocketIO
    suspend fun sendMes(chatterId: String, content: String): Message? {

        return null
    }
}
