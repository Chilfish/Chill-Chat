package top.chilfish.chillchat.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.chilfish.chillchat.data.chatslist.Chatter
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ResStrProvider
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val chatsRepo: ChatsListRepository,
    private val userRepo: UserRepository,
    private val mesRepo: MessageRepository,
    private val resStr: ResStrProvider,
    @IODispatcher
    private val ioDispatchers: CoroutineDispatcher
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    init {
        load()
        Log.d("Chat", "mainViewModel init")
    }

    private fun load() = viewModelScope.launch {
//        launch { chatsRepo.loadAll() }
//        launch { loadChats() }
        withContext(ioDispatchers) {
            watchMe()
        }
        withContext(ioDispatchers) {
            contactsRepo.loadAll(mainState.value.me?.id)
        }
    }

    private suspend fun loadChats() {
        chatsRepo.getAll()
            .flowOn(Dispatchers.IO)
            .collect { chats ->
                _mainState.update {
                    it.copy(chats = chats)
                }
            }
    }

    private suspend fun watchMe() {
        contactsRepo.getUser()
            .collect { me ->
                _mainState.update {
                    it.copy(me = me)
                }
            }
    }

    fun logout() = viewModelScope.launch {
        AccountProvider.setLogout()
    }
}

data class MainState(
    val chats: MutableList<Chatter> = mutableListOf(),
    val me: Profile? = null,

    val isLoading: Boolean = true,
)
