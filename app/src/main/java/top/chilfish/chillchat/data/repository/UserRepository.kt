package top.chilfish.chillchat.data.repository

import top.chilfish.chillchat.data.contacts.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(

) : BaseApiClient() {
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

data class LoginRequest(
    val username: String,
    val password: String
)
