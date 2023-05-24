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
import retrofit2.http.PUT
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
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): Profile?

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body profile: Profile): Boolean

    @DELETE("users/{id}/{chatterId}")
    suspend fun delContact(
        @Path("id") userId: String,
        @Path("chatterId") chatterId: String
    ): Boolean

    @GET("users/chatters/{id}")
    suspend fun loadContact(@Path("id") id: String): List<Profile>

    // Chats
    @GET("list/chats/{id}")
    suspend fun loadChats(@Path("id") id: String): List<Chats>

    // Messages
    @GET("list/message/{id}")
    suspend fun loadMessage(@Path("id") id: String): List<Message>

    // Login
    @POST("auth/login")
    suspend fun login(@Body req: LoginRequest): Profile?

    @POST("auth/register")
    suspend fun register(@Body req: LoginRequest): Profile?

    @GET("auth/logout")
    suspend fun logout(@Query("id") id: String): Boolean
}