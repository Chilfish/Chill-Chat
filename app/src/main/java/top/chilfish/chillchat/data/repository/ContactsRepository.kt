package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.Get
import com.drake.net.Put
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import top.chilfish.chillchat.data.contacts.ContactsDao
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.provider.curCid
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

    suspend fun insert(profile: Profile) = dao.insert(profile)

    suspend fun delete(id: String) = dao.deleteById(id)

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

    suspend fun getByName(name: String) = dao.getByName(name)

    suspend fun findUser(cid: String): Profile? {
        var res: Profile? = null
        withContext(ioDispatchers) {
            try {
                res = Get<Profile>("/users/$cid").await()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Chat", "net: ${e.message}")
            }
        }
        return res
    }

    suspend fun update(profile: Profile): Boolean {
        return try {
            withContext(ioDispatchers) {
                val res = Put<String>("/users/up/${profile.id}") {
                    json(Json.encodeToString(profile))
                }.await()
            }
            dao.update(profile)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun loadAll(id: String?) {
        val res = request {
            Get<List<Profile>>("/users/chatters/${id}")
        } ?: return
        Log.d("Chat", "allContacts: $res")

        dao.deleteAll()
        res.forEach { dao.insert(it) }
    }
}
