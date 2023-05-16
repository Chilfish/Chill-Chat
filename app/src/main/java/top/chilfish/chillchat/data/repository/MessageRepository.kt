package top.chilfish.chillchat.data.repository

import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.messages.MessageDao
import top.chilfish.chillchat.provider.curUid
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val dao: MessageDao
) {
    fun getAll(chatterId: Long) = dao.getAll(curUid, chatterId)

    suspend fun insert(message: Message) = dao.insert(message)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun deleteByChatterId(chatterId: Long) = dao.deleteByChatterId(chatterId)

    suspend fun deleteAll() = dao.deleteAll()
}