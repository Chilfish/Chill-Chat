package top.chilfish.chillchat.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import top.chilfish.chillchat.data.chatslist.Chats
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.provider.BaseHost
import top.chilfish.chillchat.utils.showToast

abstract class BaseApiClient {
    private val retrofitFlow: Flow<Retrofit> = BaseHost
        .map { newBaseUrl ->
            Retrofit.Builder()
                .baseUrl(newBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    private val apiServiceFlow: Flow<ApiServer> =
        retrofitFlow
            .map { retrofit ->
                retrofit.create(ApiServer::class.java)
            }

    suspend fun withApiService(block: suspend (ApiServer) -> Unit) {
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

interface ApiServer {
    // Contact
    @GET("users/{uid}")
    suspend fun getUser(@Path("uid") userId: Long): Profile?

    @POST("users/update/{uid}")
    suspend fun updateUser(@Path("uid") userId: Long, @Body profile: Profile): Boolean

    @DELETE("del/contact")
    suspend fun delContact(@Query("uid") userId: Long, @Query("chatterId") chatterId: Long): Boolean

    @GET("list/contact/{uid}")
    suspend fun loadContact(@Path("uid") uid: Long): List<Profile>

    // Chats
    @GET("list/chats/{uid}")
    suspend fun loadChats(@Path("uid") uid: Long): List<Chats>

    // Messages
    @GET("list/message/{uid}")
    suspend fun loadMessage(@Path("uid") uid: Long): List<Message>

    // Login
    @POST("login")
    suspend fun login(@Body req: LoginRequest): Profile?

    @POST("register")
    suspend fun register(@Body req: LoginRequest): Profile?

    @GET("logout")
    suspend fun logout(@Query("uid") id: Long): Boolean
}