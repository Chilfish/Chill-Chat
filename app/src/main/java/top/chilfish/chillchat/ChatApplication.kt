package top.chilfish.chillchat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ContextProvider
import top.chilfish.chillchat.provider.RepoProvider
import javax.inject.Inject

@HiltAndroidApp
class ChatApplication : Application() {
    @Inject
    lateinit var applicationScope: CoroutineScope

    @Inject
    lateinit var db: ChillChatDatabase

    override fun onCreate() {
        super.onCreate()

        ContextProvider.init(this)
        RepoProvider.init(db, applicationScope)
        AccountProvider.init(this)
    }
}