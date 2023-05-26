package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.Post
import com.drake.net.exception.RequestParamsException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.module.IODispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    @IODispatcher
    private val ioDispatchers: CoroutineDispatcher
) {
    suspend fun login(username: String, password: String): Profile? {
        var res: Profile? = null
        withContext(ioDispatchers) {
            try {
                res = Post<Profile>("/users/login") {
                    json("""{"username":"$username","password":"$password"}""")
                }.await()
            } catch (e: RequestParamsException) {
                Log.d("Chat", "Login failed: ${e.message}")
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return res
    }

    suspend fun register(username: String, password: String): Profile? {
        var res: Profile? = null

//            res = apiService.register(LoginRequest(username, password))

        return res
    }

    suspend fun logout(id: String): Boolean {
        var res = false
//        withApiService { apiService ->
//            res = apiService.logout(id)
//        }
        return res
    }
}

data class LoginRequest(
    val username: String,
    val password: String
)
