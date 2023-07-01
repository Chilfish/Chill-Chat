package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.Delete
import com.drake.net.Get
import com.drake.net.Put
import com.drake.net.exception.RequestParamsException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
    private val resStr: ResStrProvider,
    private val api: ApiRequest,

    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {
    fun allUsers() = dao.getAll()

    suspend fun deleteAll() = try {
        dao.deleteAll()
    } catch (e: Exception) {
        e.printStackTrace()
    }

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
            api.request {
                Get<Profile>("/users/$cid")
            }
        } catch (e: RequestParamsException) {
            showToast(resStr.getString(R.string.user_not_found))
            null
        }
        return res
    }

    suspend fun update(profile: Profile?): Boolean {
        if (profile == null) return false
        val res = try {
            api.request {
                Put<Boolean>("/users/up") {
                    json(Json.encodeToString(profile))
                }
            }
        } catch (e: RequestParamsException) {
            showToast(resStr.getString(R.string.update_failed))
            null
        } ?: false
        if (res) dao.update(profile)
        return res
    }

    suspend fun loadAll() = withContext(ioDispatcher) {
        val res = try {
            api.request {
                Get<List<Profile>>("/users/chatters/${curId}")
            }
        } catch (e: RequestParamsException) {
            showToast(resStr.getString(R.string.error_404))
            null
        } ?: return@withContext

        Log.d("Chat", "allContacts: $res")

        dao.deleteAll()
        res.forEach { dao.insert(it) }
    }

    suspend fun add2Contact(chatter: Profile): Boolean {
        val res = try {
            api.request {
                Put<Profile>("/users/add/${curId}/${chatter.id}")
            }
        } catch (e: RequestParamsException) {
            showToast(resStr.getString(R.string.error_404))
            null
        }

        if (res != null) {
            dao.insert(chatter)
        }
        return res != null
    }

    suspend fun delChatter(chatterId: String): Boolean {
        val res = try {
            api.request {
                Delete<Profile>("/users/del/${curId}/${chatterId}")
            }
        } catch (e: RequestParamsException) {
            showToast(resStr.getString(R.string.error_404))
            null
        }
        if (res != null) {
            dao.deleteById(chatterId)
        }
        Log.d("Chat", "del: $res")
        return res != null
    }
}
