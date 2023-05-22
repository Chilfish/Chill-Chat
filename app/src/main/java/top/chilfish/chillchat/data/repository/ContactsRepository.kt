package top.chilfish.chillchat.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import top.chilfish.chillchat.data.contacts.ContactsDao
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.provider.curUid
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepository @Inject constructor(
    private val dao: ContactsDao
) : BaseApiClient<ApiContact>() {
    override val getApiServiceClass = ApiContact::class.java

    fun allUsers() = dao.getAll()

    suspend fun insert(profile: Profile) = dao.insert(profile)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun getById(id: Long) = dao.getById(id)

    fun getUser() = dao.getUser()

    suspend fun getByName(name: String) = dao.getByName(name)

    suspend fun findUser(userId: Long): Profile? {
        var res: Profile? = null
        withApiService { apiService ->
            res = apiService.getUser(userId)
        }
        return res
    }

    suspend fun update(profile: Profile): Boolean {
        var res = false
        withApiService { apiService ->
            res = apiService.update(profile.id, profile)
        }
        if (res) dao.update(profile)
        return res
    }

    suspend fun loadAll(): Flow<MutableList<Profile>> {
        withApiService { apiContact ->
            val res = apiContact.loadAll(curUid)
            Log.d("Chat", "repo: all contacts: ${res.size}")
            dao.deleteAll()
            res.forEach { dao.insert(it) }

            val user = apiContact.getUser(curUid)
            dao.insert(user!!)
        }
        return dao.getAll()
    }

}

interface ApiContact {
    @GET("users/{uid}")
    suspend fun getUser(@Path("uid") userId: Long): Profile?

    @POST("users/update/{uid}")
    suspend fun update(@Path("uid") userId: Long, @Body profile: Profile): Boolean

    @DELETE("del/contact")
    suspend fun delete(@Query("uid") userId: Long, @Query("chatterId") chatterId: Long): Boolean

    @GET("list/contact/{uid}")
    suspend fun loadAll(@Path("uid") uid: Long): List<Profile>
}