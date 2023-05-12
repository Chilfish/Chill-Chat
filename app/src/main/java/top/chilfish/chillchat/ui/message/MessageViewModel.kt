package top.chilfish.chillchat.ui.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.navigation.NavigationActions
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.provider.RepoProvider
import top.chilfish.chillchat.utils.toJson

class MessageViewModel(
    private val profile: Profile,
    private val navHostController: NavHostController,
) : ViewModel() {
    private val _messageState = MutableStateFlow(MessageState())
    val messageState: StateFlow<MessageState> = _messageState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        RepoProvider.messRepo.getAll(profile.id).collect {
            Log.d("Chat", "messages: ${it.size}")

            _messageState.value = _messageState.value.copy(
                curProfile = profile,
                messages = it.toMutableList(),
            )
        }
    }

    fun navToProfile() {
        NavigationActions(navHostController).navigateTo(
            route = Routers.Profile,
            data = toJson(profile),
        )
    }

    fun back() = navHostController.popBackStack()

    fun more() {}

}

data class MessageState(
    val curProfile: Profile = Profile(),
    val messages: MutableList<Message> = mutableListOf(),
)