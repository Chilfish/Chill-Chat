package top.chilfish.chillchat.provider

import android.app.Application

object ContextProvider {
    lateinit var context: Application
        private set

    fun init(application: Application) {
        this.context = application
    }
}