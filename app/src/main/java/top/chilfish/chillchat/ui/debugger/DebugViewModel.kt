package top.chilfish.chillchat.ui.debugger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.provider.SettingsProvider
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
) : ViewModel() {
    fun setHost(host: String) = viewModelScope.launch {
        SettingsProvider.setHost(host)
    }

    fun find() = viewModelScope.launch {
        val id = "chilfish"
        val res = contactsRepo.findUser(id)
        Log.d("Chat", "debug: User: $res")
//        contactsRepo.update(res!!)
        contactsRepo.loadAll()
    }
}