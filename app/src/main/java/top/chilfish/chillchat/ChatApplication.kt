package top.chilfish.chillchat

import android.app.Application
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ContextProvider

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextProvider.init(this)
        AccountProvider.init(this)
    }
}