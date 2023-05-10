package top.chilfish.chillchat

import android.app.Application
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ContextProvider
import top.chilfish.chillchat.provider.DatabaseProvider

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextProvider.init(this)
        DatabaseProvider.init(this)
        AccountProvider.init(this)
    }
}