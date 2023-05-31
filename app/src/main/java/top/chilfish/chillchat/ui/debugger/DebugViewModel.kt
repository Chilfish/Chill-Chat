package top.chilfish.chillchat.ui.debugger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.SettingsProvider
import top.chilfish.chillchat.provider.curCid
import top.chilfish.chillchat.provider.curId
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val userRepo: UserRepository,
    private val mesRepo: MessageRepository,
    private val socket: Socket,
) : ViewModel() {
    init {
        Log.d("Chat", "Socket: ${socket.connected()}")
        connect()
    }

    private fun connect() {
        socket.connect()
        socket.emit("join", curId)
        socket.on("connect") {
            Log.d("Chat", "Socket: Connected!")
        }
        socket.on("message") { args ->
            Log.d("Chat", "receive message: ${args[0]}")
        }
    }

    fun join() {
        socket.emit("join", curId)
    }

    fun setHost(host: String) = viewModelScope.launch {
        SettingsProvider.setHost(host)
    }

    fun find(cid: String) = viewModelScope.launch {
        val res = contactsRepo.findUser(cid)
        Log.d("Chat", "debug: User: $res")
    }

    fun setCid(cid: String) = viewModelScope.launch {
        val res = contactsRepo.findUser(cid) ?: return@launch
        AccountProvider.setLogin(res.id, cid)
    }

    fun loadContacts() = viewModelScope.launch {
//        contactsRepo.allUsers().collect {
//            Log.d("Chat", "debug: Contacts: $it")
//        }
        contactsRepo.loadAll()
    }

    fun login() = viewModelScope.launch {
        val res = userRepo.auth("chilfish", "12345678")
        Log.d("Chat", "login:$res")
    }

    fun uploadImg(img: File) = viewModelScope.launch {
        userRepo.updateAvatar(img)
    }

    fun sendMes() = viewModelScope.launch {
        mesRepo.sendMes("", "Hello")
    }
}