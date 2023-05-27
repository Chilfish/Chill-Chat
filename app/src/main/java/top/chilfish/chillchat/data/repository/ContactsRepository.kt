package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.Delete
import com.drake.net.Get
import com.drake.net.Put
import com.drake.net.exception.RequestParamsException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.ContactsDao
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.provider.curCid
import top.chilfish.chillchat.provider.curId
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ContactsRepository @Inject constructor(
    private val dao: ContactsDao,
    @IODispatcher
    private val ioDispatchers: CoroutineDispatcher,
    private val resStr: ResStrProvider,
) : BaseApiRequest(resStr) {

    fun allUsers() = dao.getAll()

    suspend fun getById(id: String) = dao.getById(id)

    suspend fun setUser(me: Profile) {
        val old = dao.getById(me.id)
        if (old == null) {
            dao.insert(me)
        } else {
            dao.update((me))
        }
        Log.d("Chat", "setUser: $me, old: ${old?.id}, curCid: $curCid")
    }

    fun getUser() = dao.getUser()

    suspend fun findUser(cid: String): Profile? {
        val res = try {
            request {
                Get<Profile>("/users/$cid")
            }
        } catch (e: RequestParamsException) {
            showToast(resStr.getString(R.string.user_not_found))
            null
        }
        return res
    }

    suspend fun update(profile: Profile): Boolean {
        val res =
            request {
                Put<Boolean>("/users/up/${profile.id}") {
                    json(Json.encodeToString(profile))
                }
            }
        dao.update(profile)
        return res ?: false
    }

    suspend fun loadAll(id: String?) {
        val res = request {
            Get<List<Profile>>("/users/chatters/${id}")
        } ?: return
        Log.d("Chat", "allContacts: $res")

        dao.deleteAll()
        res.forEach { dao.insert(it) }
    }

    suspend fun add2Contact(chatter: Profile): Boolean {
        val res = request {
            Put<Profile>("/users/add/${curId}/${chatter.id}")
        }
        if (res != null) {
            dao.insert(chatter)
        }
        return res != null
    }

    suspend fun delChatter(chatterId: String): Boolean {
        val res = request {
            Delete<Profile>("/users/del/${curId}/${chatterId}")
        }
        if (res != null) {
            dao.deleteById(chatterId)
        }
        Log.d("Chat", "del: $res")
        return res != null
    }
}
