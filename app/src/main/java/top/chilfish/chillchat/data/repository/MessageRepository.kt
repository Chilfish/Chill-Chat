package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.Get
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.messages.MessageDao
import top.chilfish.chillchat.data.messages.MessagesItem
import top.chilfish.chillchat.data.module.ApplicationScope
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.provider.curId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val dao: MessageDao,
    private val chatsRepo: ChatsListRepository,
    private val api: ApiRequest,

    @ApplicationScope
    private val scope: CoroutineScope,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {
    private lateinit var socket: Socket

    fun init(socket: Socket) {
        this.socket = socket
    }

    fun getAll(chatterId: String) = dao.getAll(curId, chatterId)

    suspend fun insert(message: Message) = dao.insert(message)

    suspend fun delete(id: String) = dao.deleteById(id)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun loadAll() = withContext(ioDispatcher) {
        val res = api.request {
            Get<List<MessagesItem>>("/messages/${curId}")
        } ?: return@withContext

        dao.deleteAll()
        res.forEach {
//            Log.d("Chat", "fetch messages: ${it.messages}")
            dao.insertAll(it.messages)
        }
    }

    private suspend fun insertAndUpdate(mes: Message, chatterId: String) =
        withContext(ioDispatcher) {
            launch { chatsRepo.updateById(chatterId, mes.content, mes.time) }
            launch { dao.insert(mes) }
            Unit
        }

    suspend fun sendMes(chatterId: String, content: String) = withContext(ioDispatcher) {
        val message = Message(
            sendId = curId,
            receiveId = chatterId,
            content = content
        )

        socket.emit("send_message", Json.encodeToString(message))
        insertAndUpdate(message, chatterId)
    }

    suspend fun receiveMes() = withContext(ioDispatcher) {
        socket.on("message") { args ->
            val message = Json.decodeFromString<Message>(args[0].toString())
            Log.d("Chat", "receive: ${args[0]}")

            scope.launch {
                insertAndUpdate(message, message.sendId)
            }
        }
        Unit
    }

    suspend fun isRead(chatterId: String) = withContext(ioDispatcher) {
        socket.emit("read", """{"userId":"$curId","chatterId":"$chatterId"}""")
        Unit
    }
}
