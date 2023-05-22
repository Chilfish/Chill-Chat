package top.chilfish.chillchat.data.repository

import android.util.Log
import retrofit2.http.GET
import retrofit2.http.Path
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.messages.MessageDao
import top.chilfish.chillchat.provider.curUid
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val dao: MessageDao
) : BaseApiClient<ApiMessage>() {
    override val getApiServiceClass = ApiMessage::class.java

    fun getAll(chatterId: Long) = dao.getAll(curUid, chatterId)

    suspend fun insert(message: Message) = dao.insert(message)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun deleteByChatterId(chatterId: Long) = dao.deleteByChatterId(chatterId)

    suspend fun deleteAll() = dao.deleteAll()

    // should be loaded in mainPage
    suspend fun loadAll() {
        withApiService { apiMessage ->
            val res = apiMessage.loadAll(curUid)
            Log.d("Chat", "repo: all mes: ${res.size}")
            dao.deleteAll()
            res.forEach { dao.insert(it) }
        }
    }
}

interface ApiMessage {
    @GET("list/message/{uid}")
    suspend fun loadAll(@Path("uid") uid: Long): List<Message>
}