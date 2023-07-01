package top.chilfish.chillchat

import android.app.Application
import com.drake.net.NetConfig
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Cache
import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.data.module.ApplicationScope
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ContextProvider
import top.chilfish.chillchat.utils.SerializationConverter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// 服务器地址
const val BaseHost = "http://as.chilfish.top:8000"

@HiltAndroidApp
class ChatApplication : Application() {

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var db: ChillChatDatabase

    @Inject
    lateinit var chatsListRepository: ChatsListRepository

    @Inject
    lateinit var contactsRepository: ContactsRepository

    @Inject
    lateinit var messageRepository: MessageRepository

    override fun onCreate() {
        super.onCreate()

        ContextProvider.init(this)
        AccountProvider.init(this)

        // 初始化 Net 请求库
        applicationScope.launch {
            NetConfig.initialize(BaseHost, applicationContext) {
                connectTimeout(5, TimeUnit.SECONDS)
                setDebug(true)
                setConverter(SerializationConverter())
                cache(Cache(cacheDir, 1024 * 1024 * 128))
            }
        }
    }
}