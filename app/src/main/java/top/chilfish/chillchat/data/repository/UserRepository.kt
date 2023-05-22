package top.chilfish.chillchat.data.repository

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import top.chilfish.chillchat.data.contacts.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(

) : BaseApiClient<ApiUser>() {
    override val getApiServiceClass = ApiUser::class.java

    suspend fun login(username: String, password: String): Profile? {
        var res: Profile? = null
        withApiService { apiService ->
            res = apiService.login(LoginRequest(username, password))
        }
        return res
    }

    suspend fun register(username: String, password: String): Profile? {
        var res: Profile? = null
        withApiService { apiService ->
            res = apiService.register(LoginRequest(username, password))
        }
        return res
    }

    suspend fun logout(id: Long): Boolean {
        var res = false
        withApiService { apiService ->
            res = apiService.logout(id)
        }
        return res
    }
}

interface ApiUser {
    @POST("login")
    suspend fun login(@Body req: LoginRequest): Profile?

    @POST("register")
    suspend fun register(@Body req: LoginRequest): Profile?

    @GET("logout")
    suspend fun logout(id: Long): Boolean
}

data class LoginRequest(
    val username: String,
    val password: String
)
