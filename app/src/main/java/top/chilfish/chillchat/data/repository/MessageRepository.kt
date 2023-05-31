package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.Get
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.messages.MessageDao
import top.chilfish.chillchat.data.module.ApplicationScope
import top.chilfish.chillchat.provider.curId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val dao: MessageDao,
    private val contactsRepository: ContactsRepository,
    private val socket: Socket,
    private val api: ApiRequest,

    @ApplicationScope
    private val scope: CoroutineScope,
) {
    fun getAll(chatterId: String) = dao.getAll(curId, chatterId)

    suspend fun insert(message: Message) = dao.insert(message)

    suspend fun delete(id: String) = dao.deleteById(id)

    suspend fun deleteByChatterId(chatterId: String) = dao.deleteByChatterId(chatterId)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun loadAll() {
        contactsRepository.allUsers().collect { contacts ->
            contacts
                .filter { it.id != curId }
                .forEach {
                    suspend { loadMessages(it) }
                }
        }
    }

    private suspend fun loadMessages(contact: Profile) {
        val res = api.request {
            Get<List<Message>>("/messages/${curId}/${contact.id}")
        } ?: return
        dao.insertAll(res)
    }

    fun sendMes(chatterId: String, content: String): Message {
        val message = Message(
            sendId = curId,
            receiveId = chatterId,
            content = content
        )

        socket.emit("send_message", Json.encodeToString(message))
        return message
    }

    fun receiveMes() {
        socket.on("message") { args ->
            val message = Json.decodeFromString<Message>(args[0].toString())
            Log.d("Chat", "receive: ${args[0]}")
            scope.launch {
                insert(message)
            }
        }
    }
}
