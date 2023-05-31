package top.chilfish.chillchat.data.repository

import android.util.Log
import com.drake.net.exception.NetConnectException
import com.drake.net.exception.NetSocketTimeoutException
import com.drake.net.exception.RequestParamsException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiRequest @Inject constructor(
    private val resStr: ResStrProvider,

    @IODispatcher
    private val ioDispatchers: CoroutineDispatcher,
) {
    suspend fun <T> request(request: suspend CoroutineScope.() -> Deferred<T>): T? {
        var res: T? = null
        withContext(ioDispatchers) {
            res = try {
                request().await()
            } catch (e: RequestParamsException) {
                e.printStackTrace()
                throw e
            } catch (e: NetSocketTimeoutException) {
                e.printStackTrace()
                Log.e("Chat", "net error: ${e.cause}")
                showToast(resStr.getString(R.string.error_server))
                null
            } catch (e: NetConnectException) {
                e.printStackTrace()
                Log.e("Chat", "net error: ${e.cause}")
                showToast(resStr.getString(R.string.error_network))
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return res
    }
}