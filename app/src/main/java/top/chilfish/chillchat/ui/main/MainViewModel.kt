package top.chilfish.chillchat.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import top.chilfish.chillchat.provider.AccountProvider

class MainViewModel : ViewModel() {

    fun logout() = viewModelScope.launch {
        AccountProvider.setLogout()
    }
}