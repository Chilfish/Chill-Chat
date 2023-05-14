package top.chilfish.chillchat.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import top.chilfish.chillchat.BaseActivity
import top.chilfish.chillchat.navigation.ChillNavHost
import top.chilfish.chillchat.provider.isLoggedIn
import top.chilfish.chillchat.ui.login.LoginActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel: MainViewModel by viewModels()

            ChillNavHost(
                navController = navController,
                viewModel = viewModel
            )
        }

        lifecycleScope.launch {
            isLoggedIn.collect {
                if (!it) {
                    startActivity<LoginActivity>()
                    finish()
                }
            }
        }
    }
}
