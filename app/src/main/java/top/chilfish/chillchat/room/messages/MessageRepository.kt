package top.chilfish.chillchat.room.messages

import top.chilfish.chillchat.data.Message
import top.chilfish.chillchat.provider.curUid

class MessageRepository(private val dao: MessageDao) {
    fun getAll(chatterId: Long) = dao.getAll(curUid, chatterId)

    suspend fun insert(message: Message) = dao.insert(message)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun deleteByChatterId(chatterId: Long) = dao.deleteByChatterId(chatterId)

    suspend fun deleteAll() = dao.deleteAll()
}