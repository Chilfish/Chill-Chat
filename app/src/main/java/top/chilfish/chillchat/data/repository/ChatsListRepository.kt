package top.chilfish.chillchat.data.repository


import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import top.chilfish.chillchat.data.chatslist.Chats
import top.chilfish.chillchat.data.chatslist.ChatsListDao
import top.chilfish.chillchat.data.messages.MessageDao
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.provider.curId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatsListRepository @Inject constructor(
    private val dao: ChatsListDao,
    private val mesDao: MessageDao,

    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {
    fun getAll() = dao.getAll()

    suspend fun insert(chats: Chats) = dao.insert(chats)

    suspend fun delete(id: String) = dao.deleteById(id)

    suspend fun update(chats: Chats) = dao.update(chats)

    suspend fun updateById(chatterId: String, message: String, time: Long) =
        withContext(ioDispatcher) {
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

    suspend fun loadAll() = withContext(ioDispatcher) {
        val lastMessages = mesDao.getLatest()
        dao.deleteAll()

        Log.d("Chat", "load ChatsList: $lastMessages")

        lastMessages.forEach { mes ->
            dao.insert(
                Chats(
                    chatterId = if (mes.sendId == curId) mes.receiveId else mes.sendId,
                    lastTime = mes.time,
                    lastMessage = mes.content,
                )
            )
        }

        dao.getAll()
    }
}
