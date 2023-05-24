package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.Get
import com.drake.net.Put
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import top.chilfish.chillchat.data.contacts.ContactsDao
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.utils.toJson
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ContactsRepository @Inject constructor(
    private val dao: ContactsDao,
    @IODispatcher
    private val ioDispatchers: CoroutineDispatcher
) {

    fun allUsers() = dao.getAll()

    suspend fun insert(profile: Profile) = dao.insert(profile)

    suspend fun delete(id: String) = dao.deleteById(id)

    suspend fun getById(id: String) = dao.getById(id)

    fun getUser() = dao.getUser()

    suspend fun getByName(name: String) = dao.getByName(name)

    suspend fun findUser(cid: String): Profile? {
        return try {
            var res: Profile?
            withContext(ioDispatchers) {
                res = Get<Profile>("/users/$cid").await()
            }
            res
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun update(profile: Profile): Boolean {
        return try {
            withContext(ioDispatchers) {
                val res = Put<String>("/users/up/${profile.id}") {
                    json(toJson(profile))
                }.await()
            }
            dao.update(profile)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun loadAll() {
        try {
            withContext(ioDispatchers) {
                val res = Get<String>("/users/chatters/646ccece9c566de7a7a9b0e5").await()
                Log.d("Chat", "allChat: $res")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


//            dao.deleteAll()
//            res.forEach { dao.insert(it) }
//            dao.insert(user!!)
    }
}
