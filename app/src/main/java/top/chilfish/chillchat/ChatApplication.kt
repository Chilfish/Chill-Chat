package top.chilfish.chillchat

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ContextProvider
import top.chilfish.chillchat.provider.RepoProvider

class ChatApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        val database by lazy {
            ChillChatDatabase.getDatabase(this, applicationScope)
        }

        ContextProvider.init(this)
        RepoProvider.init(database)
        AccountProvider.init(this)
    }
}