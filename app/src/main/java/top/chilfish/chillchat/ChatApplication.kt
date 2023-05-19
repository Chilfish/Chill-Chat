package top.chilfish.chillchat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ContextProvider
import top.chilfish.chillchat.provider.SettingsProvider
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

    override fun onCreate() {
        super.onCreate()

        ContextProvider.init(this)
        SettingsProvider.init(this)
        AccountProvider.init(this)
    }
}