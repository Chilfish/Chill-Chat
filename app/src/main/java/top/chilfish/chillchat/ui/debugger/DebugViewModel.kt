package top.chilfish.chillchat.ui.debugger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import top.chilfish.chillchat.provider.SettingsProvider
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(

) : ViewModel() {
    fun setHost(host: String) = viewModelScope.launch {
        SettingsProvider.setHost(host)
    }
}