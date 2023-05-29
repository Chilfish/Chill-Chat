package top.chilfish.chillchat

import android.app.Application
import com.drake.net.NetConfig
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import dagger.hilt.android.HiltAndroidApp
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Cache
import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.BaseHost
import top.chilfish.chillchat.provider.ContextProvider
import top.chilfish.chillchat.provider.SettingsProvider
import top.chilfish.chillchat.utils.SerializationConverter
import javax.inject.Inject

@HiltAndroidApp
class ChatApplication : Application() {
    @Inject
    lateinit var applicationScope: CoroutineScope

    @Inject
    lateinit var db: ChillChatDatabase

    @Inject
    lateinit var chatsListRepository: ChatsListRepository

    @Inject
    lateinit var contactsRepository: ContactsRepository

    @Inject
    lateinit var messageRepository: MessageRepository

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var socket: Socket

    override fun onCreate() {
        super.onCreate()

        ContextProvider.init(this)
        SettingsProvider.init(this)
        AccountProvider.init(this)

        applicationScope.launch {
            BaseHost.collect {
                NetConfig.initialize(it, applicationContext) {
                    setDebug(true)
                    setConverter(SerializationConverter())
                    cache(Cache(cacheDir, 1024 * 1024 * 128))
                }
            }
        }
    }
}