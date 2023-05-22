package top.chilfish.chillchat.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import top.chilfish.chillchat.data.chatslist.Chats
import top.chilfish.chillchat.data.chatslist.ChatsListDao
import top.chilfish.chillchat.data.chatslist.Chatter
import top.chilfish.chillchat.provider.curUid
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ChatsListRepository @Inject constructor(
    private val dao: ChatsListDao
) : BaseApiClient<ApiChats>() {
    override val getApiServiceClass = ApiChats::class.java

    fun getAll() = dao.getAll()

    suspend fun insert(chats: Chats) = dao.insert(chats)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(chats: Chats) = dao.update(chats)

    suspend fun updateById(chatterId: Long, message: String, time: Long) =
        dao.updateById(chatterId, message, time)

    suspend fun loadAll() {
        withApiService { apiService ->
            val res = apiService.loadAll(curUid)
            Log.d("Chat", "repo: all chats: ${res}")
            dao.deleteAll()
            res.forEach { dao.insert(it) }
        }
    }
}

interface ApiChats {
    @GET("list/chats/{uid}")
    suspend fun loadAll(@Path("uid") uid: Long): List<Chats>
}