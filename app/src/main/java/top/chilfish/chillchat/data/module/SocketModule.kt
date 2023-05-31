package top.chilfish.chillchat.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.provider.BaseHost
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideSocket(
        @ApplicationScope scope: CoroutineScope,
    ): Socket {
        val socketFlow = MutableStateFlow(IO.socket("${BaseHost.value}/chat"))
        scope.launch {
            BaseHost.collect { newHost ->
                val newSocket = IO.socket("$newHost/chat")
                socketFlow.emit(newSocket)
            }
        }
        return socketFlow.value
    }
}