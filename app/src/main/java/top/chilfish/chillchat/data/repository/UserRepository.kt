package top.chilfish.chillchat.data.repository

import com.drake.net.Post
import com.drake.net.Put
import com.drake.net.exception.RequestParamsException
import okhttp3.Response
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.provider.curId
import top.chilfish.chillchat.utils.showToast

@Singleton
class UserRepository @Inject constructor(
    private val resStr: ResStrProvider,
    private val api: ApiRequest,
) {
    suspend fun auth(username: String, password: String, isLogin: Boolean = true): Profile? {
        val path = if (isLogin) "login" else "register"
        val res = try {
            api.request {
                Post<Profile>("/users/${path}") {
                    json("""{"username":"$username","password":"$password"}""")
                }
            }
        } catch (e: RequestParamsException) {
            when (e.response.code) {
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
            null
        }
        return res
    }

    suspend fun updatePassword(old: String, new: String): Boolean {
        val res = try {
            api.request {
                Put<Boolean>("/users/up/password/${curId}") {
                    json("""{"old":"$old", "new":"$new"}""")
                }
            }
        } catch (e: RequestParamsException) {
            if (e.response.code == 400) {
                showToast(resStr.getString(R.string.error_password_same))
            }
            null
        } ?: false
        return res
    }

    suspend fun updateAvatar(avatar: File): String? {
        val res = api.request {
            Post<String>("/file") {
                param("file", avatar.name, avatar)
            }
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
