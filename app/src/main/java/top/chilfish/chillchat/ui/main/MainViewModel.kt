package top.chilfish.chillchat.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.Chats
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.DatabaseProvider

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
        DatabaseProvider.chatsListRepository.allChats.collect {
            _mainState.value = _mainState.value.copy(chats = it)
        }
        DatabaseProvider.contactsRepository.allUsers.collect {
            _mainState.value = _mainState.value.copy(contacts = it)
        }
        val curUser = DatabaseProvider.contactsRepository.getUser()
        _mainState.value = _mainState.value.copy(me = curUser)
    }

    fun logout() = viewModelScope.launch {
        AccountProvider.setLogout()
    }

    fun navToMessage(chat: Chats) {

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
