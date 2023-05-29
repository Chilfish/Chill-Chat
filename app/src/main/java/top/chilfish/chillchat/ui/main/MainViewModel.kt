package top.chilfish.chillchat.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.chatslist.Chatter
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.provider.curId
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val chatsRepo: ChatsListRepository,
    private val mesRepo: MessageRepository,
    private val resStr: ResStrProvider,
    private val userRepo: UserRepository,
    private val socket: Socket,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    init {
        load()
        Log.d("Chat", "mainViewModel init")
    }

    fun load() = viewModelScope.launch {
        connect()
        launch { watchMe() }
        launch { contactsRepo.loadAll(mainState.value.me?.id) }
        launch { mesRepo.loadAll() }
        launch { loadChats() }
    }

    private fun connect() {
        socket.connect()
        socket.on("connect") {
            Log.d("Chat", "Socket: Connected!")
        }
        socket.emit("join", curId)
    }

    private suspend fun loadChats() {
        chatsRepo.loadAll()
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

    fun changeAvatar(avatar: File?) = viewModelScope.launch {
        if (avatar == null) return@launch

        val res = userRepo.updateAvatar(avatar) ?: return@launch

        Log.d("Chat", "Avatar: $avatar, $res")
        contactsRepo.update(mainState.value.me?.copy(avatar = res))
    }
}

data class MainState(
    val chats: MutableList<Chatter> = mutableListOf(),
    val me: Profile? = null,

    val isLoading: Boolean = true,
)
