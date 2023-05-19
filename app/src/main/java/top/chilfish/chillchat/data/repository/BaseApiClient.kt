package top.chilfish.chillchat.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.chilfish.chillchat.provider.BaseHost

abstract class BaseApiClient<T> {
    private val retrofitFlow: Flow<Retrofit> = BaseHost
        .map { newBaseUrl ->
            Retrofit.Builder()
                .baseUrl(newBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    private val apiServiceFlow: Flow<T> = retrofitFlow
        .map { retrofit -> retrofit.create(getApiServiceClass()) }

    protected abstract fun getApiServiceClass(): Class<T>

    suspend fun withApiService(block: suspend (T) -> Unit) {
        val apiService = apiServiceFlow.first()
        block(apiService)
    }
}