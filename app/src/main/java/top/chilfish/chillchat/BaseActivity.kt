package top.chilfish.chillchat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import top.chilfish.chillchat.ui.theme.ChillChatTheme

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    protected fun setContent(content: @Composable () -> Unit) {
        setContent(
            parent = null,
            content = {
                ChillChatTheme {
                    content()
                }
            }
        )
    }

    protected inline fun <reified T : Activity> startActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}