package top.chilfish.chillchat.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import top.chilfish.chillchat.provider.BaseHost
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideSocket(): Socket = IO.socket("${BaseHost.value}/chat")
}