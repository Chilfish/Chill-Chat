package top.chilfish.chillchat.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.chatslist.Chatter
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.RepoProvider
import top.chilfish.chillchat.provider.curUid

class MainViewModel : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        val contactsDeferred = async { RepoProvider.contactsRepo.allUsers() }
        val curUserDeferred = async { RepoProvider.contactsRepo.getUser() }

        val contacts = contactsDeferred.await()
            .filter { it.id != curUid }
            .toMutableList()

        _mainState.update {
            it.copy(
                contacts = contacts,
                me = curUserDeferred.await(),
                isLoading = false,
            )
        }

        RepoProvider.chatsRepo.getAll().collect { chats ->
            Log.d("Chat", "chats: ${chats.size}")

            _mainState.update {
                it.copy(
                    chats = chats,
                )
            }
        }
    }

    fun logout() = viewModelScope.launch {
        AccountProvider.setLogout()
    }

    fun search() {
    }

    fun addFriend() {
    }
}

data class MainState(
    val chats: MutableList<Chatter> = mutableListOf(),
    val contacts: MutableList<Profile> = mutableListOf(),
    val me: Profile? = null,

    val isLoading: Boolean = true,
)
