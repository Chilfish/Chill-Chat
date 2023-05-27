package top.chilfish.chillchat.data.repository

import com.drake.net.exception.RequestParamsException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseApiRequest @Inject constructor(
) {
    suspend fun <T> request(request: suspend CoroutineScope.() -> Deferred<T>): T? {
        var res: T? = null
        withContext(Dispatchers.IO) {
            res = try {
                request().await()
            } catch (e: RequestParamsException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return res
    }
}