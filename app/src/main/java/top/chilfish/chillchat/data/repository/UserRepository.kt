package top.chilfish.chillchat.data.repository

import com.drake.net.Post
import com.drake.net.exception.RequestParamsException
import okhttp3.Response
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val resStr: ResStrProvider,
) : BaseApiRequest() {
    suspend fun auth(username: String, password: String, isLogin: Boolean = true): Profile? {
        val path = if (isLogin) "login" else "register"
        val res = try {
            request {
                Post<Profile>("/users/${path}") {
                    json("""{"username":"$username","password":"$password"}""")
                }
            }
        } catch (e: RequestParamsException) {
            exception(e.response)
            null
        }
        return res
    }

    private fun exception(e: Response) {
        when (e.code) {
            409 -> {
                showToast(resStr.getString(R.string.exist_username))
            }

            404 -> {
                showToast(resStr.getString(R.string.error_auth))
            }

            in 500..600 -> {
                showToast(resStr.getString(R.string.error_server))
            }
        }
    }
}
