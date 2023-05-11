package top.chilfish.chillchat.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.Chats
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.DatabaseProvider
import top.chilfish.chillchat.provider.curUid

class MainViewModel(
    private val navController: NavHostController,
) : ViewModel() {
    private val _mainState = MutableStateFlow(MainState())
    val mainState: Flow<MainState> = _mainState
        .asStateFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        val contactsDeferred = async { DatabaseProvider.contactsRepository.allUsers() }
        val chatsDeferred = async { DatabaseProvider.chatsListRepository.allChats() }
        val curUserDeferred = async { DatabaseProvider.contactsRepository.getUser() }

        val contacts = contactsDeferred.await()
            .filter { it.id != curUid }
            .toMutableList()
        val chats = chatsDeferred.await()
        val curUser = curUserDeferred.await()

        _mainState.value = _mainState.value.copy(
            contacts = contacts,
            chats = chats,
            me = curUser
        )
    }

    fun logout() = viewModelScope.launch {
        AccountProvider.setLogout()
    }

    fun navToMessage(chat: Chats) {

    }

    fun navToProfile(profile: Profile) {

    }

    fun getChatProfile(id: Long): Profile {
        var chat: Profile? = null
        viewModelScope.launch {
            chat = DatabaseProvider.contactsRepository.getById(id)
        }
        return chat ?: Profile()
    }
}

class MainViewModelFactory(private val navController: NavHostController) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(navController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class MainState(
    val chats: MutableList<Chats> = mutableListOf(),
    val contacts: MutableList<Profile> = mutableListOf(),
    val me: Profile? = null,
)
