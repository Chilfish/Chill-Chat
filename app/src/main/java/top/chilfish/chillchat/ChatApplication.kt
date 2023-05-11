package top.chilfish.chillchat

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ContextProvider
import top.chilfish.chillchat.provider.DatabaseProvider

class ChatApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        ContextProvider.init(this)
        DatabaseProvider.init(this, applicationScope)
        AccountProvider.init(this)
    }
}