package top.chilfish.chillchat.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.chatslist.Chatter
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val chatsRepo: ChatsListRepository,
    private val userRepo: UserRepository,
    private val resStr: ResStrProvider,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        launch {
            chatsRepo.getAll()
                .flowOn(Dispatchers.IO)
                .collect { chats ->
                    _mainState.update {
                        it.copy(chats = chats)
                    }
                }
        }

        // collect change from repo
        launch {
            contactsRepo.getUser()
                .flowOn(Dispatchers.IO)
                .collect { me ->
                    _mainState.update {
                        it.copy(me = me)
                    }
                }
        }
    }

    fun logout() = viewModelScope.launch {
        val id = mainState.value.me!!.id
        val res = userRepo.logout(id)
        if (res) {
            AccountProvider.setLogout()
        } else {
            showToast(resStr.getString(R.string.logout_failed))
        }
    }

    fun search() {
    }
}

data class MainState(
    val chats: MutableList<Chatter> = mutableListOf(),
    val me: Profile? = null,

    val isLoading: Boolean = true,
)
