package top.chilfish.chillchat.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.chatslist.Chatter
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.curUid
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val chatsRepo: ChatsListRepository,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        val contactsDeferred = async { contactsRepo.allUsers() }
        val contacts = contactsDeferred.await()
            .filter { it.id != curUid }
            .toMutableList()

        val chats = async { chatsRepo.getAll().first() }.await()

        _mainState.update {
            it.copy(
                chats = chats,
                contacts = contacts,
                isLoading = false,
            )
        }

        // collect change from repo
        contactsRepo.getUser()
            .flowOn(Dispatchers.IO)
            .collect { me ->
                _mainState.update {
                    it.copy(me = me)
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
