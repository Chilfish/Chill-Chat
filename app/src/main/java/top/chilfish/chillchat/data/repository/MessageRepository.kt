package top.chilfish.chillchat.data.repository

import com.drake.net.Get
import com.drake.net.exception.RequestParamsException
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.messages.MessageDao
import top.chilfish.chillchat.data.messages.MessagesItem
import top.chilfish.chillchat.data.module.ApplicationScope
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.provider.curId
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val dao: MessageDao,
    private val chatsRepo: ChatsListRepository,
    private val api: ApiRequest,
    private val resStr: ResStrProvider,

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

    /**
     * 加载聊天列表
     */
    suspend fun loadAll() = withContext(ioDispatcher) {
        val res = try {
            api.request {
                Get<List<MessagesItem>>("/messages/${curId}")
            }
        } catch (e: RequestParamsException) {
            showToast(resStr.getString(R.string.error_404))
            null
        } ?: return@withContext


        dao.deleteAll()
        res.forEach {
            dao.insertAll(it.messages)
        }
    }

    /**
     * 插入消息并更新聊天列表
     */
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
            scope.launch { insertAndUpdate(message, message.sendId) }
        }
        Unit
    }

    suspend fun isRead(chatterId: String) = withContext(ioDispatcher) {
        socket.emit("read", """{"userId":"$curId","chatterId":"$chatterId"}""")
        Unit
    }
}
