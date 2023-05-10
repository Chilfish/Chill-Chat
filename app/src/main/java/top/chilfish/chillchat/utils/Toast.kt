package top.chilfish.chillchat.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import top.chilfish.chillchat.provider.ContextProvider

private val mainHandler by lazy {
    Handler(Looper.getMainLooper())
}

private fun showToast(message: String) {
    Toast.makeText(
        ContextProvider.context, message, Toast.LENGTH_SHORT
    ).show()
}

fun showToast(msg: Any?) {
    val message = msg?.toString()
    if (message.isNullOrBlank()) {
        return
    }
    if (Looper.myLooper() == Looper.getMainLooper()) {
        showToast(message = message)
    } else {
        mainHandler.post {
            showToast(message = message)
        }
    }
}