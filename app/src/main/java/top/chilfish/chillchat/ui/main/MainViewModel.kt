package top.chilfish.chillchat.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.BaseHost
import top.chilfish.chillchat.data.chatslist.Chatter
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.curId
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val chatsRepo: ChatsListRepository,
    private val mesRepo: MessageRepository,
    private val userRepo: UserRepository,

    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    private lateinit var socket: Socket

    init {
        connect()
        load()
        Log.d("Chat", "mainViewModel init")
    }

    fun load() = viewModelScope.launch {
        launch { watchMe() }
        launch { contactsRepo.loadAll() }

        //
        async { mesRepo.loadAll() }.await()
        launch { loadChats() }
    }

    private fun connect() = viewModelScope.launch(ioDispatcher) {
        socket = IO.socket("${BaseHost}/chat")
        socket.connect()
        socket.on("connect") {
            Log.d("Chat", "Socket: Connected!")
            socket.emit("join", curId)
        }
        mesRepo.init(socket)
        mesRepo.receiveMes()
    }

    private suspend fun loadChats() {
        chatsRepo.loadAll()
            .flowOn(ioDispatcher)
            .collect { chats ->
                _mainState.update {
                    it.copy(chats = chats)
                }
            }
    }

    private suspend fun watchMe() {
        contactsRepo.getUser()
            .flowOn(ioDispatcher)
            .collect { res ->
                _mainState.update {
                    it.copy(me = res)
                }
            }
    }

    fun logout() = viewModelScope.launch {
        try {
            async { chatsRepo.deleteAll() }.await()
            contactsRepo.deleteAll()
            mesRepo.deleteAll()
            AccountProvider.setLogout()
            socket.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
)
