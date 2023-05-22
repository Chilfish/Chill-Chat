package top.chilfish.chillchat.data.repository

import retrofit2.http.GET
import retrofit2.http.Path
import top.chilfish.chillchat.data.contacts.ContactsDao
import top.chilfish.chillchat.data.contacts.Profile
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

    suspend fun update(profile: Profile) = dao.update(profile)

    suspend fun getById(id: Long) = dao.getById(id)

    fun getUser() = dao.getUser()

    suspend fun getByName(name: String) = dao.getByName(name)

    suspend fun findUser(userId: String): Profile? {
        var res: Profile? = null
        withApiService { apiService ->
            res = apiService.getUser(userId)
        }
        return res
    }

}

interface ApiContact {
    @GET("users/{uid}")
    suspend fun getUser(@Path("uid") userId: String): Profile?
}