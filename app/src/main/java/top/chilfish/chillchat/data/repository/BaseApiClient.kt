package top.chilfish.chillchat.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.chilfish.chillchat.provider.BaseHost
import top.chilfish.chillchat.utils.showToast

abstract class BaseApiClient<T> {
    private val retrofitFlow: Flow<Retrofit> = BaseHost
        .map { newBaseUrl ->
            Retrofit.Builder()
                .baseUrl(newBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    private val apiServiceFlow: Flow<T> = retrofitFlow
        .map { retrofit -> retrofit.create(getApiServiceClass) }

    protected abstract val getApiServiceClass: Class<T>

    suspend fun withApiService(block: suspend (T) -> Unit) {
        try {
            val apiService = apiServiceFlow.first()
            block(apiService)
        } catch (e: HttpException) {
            e.printStackTrace()
            showToast("Network Error")
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("Unknown Error")
        } finally {
            BaseHost.collect {
                Log.d("Chat", "BaseHost: $it")
            }
        }
    }
}