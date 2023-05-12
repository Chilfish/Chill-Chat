package top.chilfish.chillchat.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile

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
        _messageState.value = _messageState.value.copy(
            curProfile = profile,
        )
    }

    fun navToProfile() {
    }

    fun back() = navHostController.popBackStack()

    fun more() {}

}

data class MessageState(
    val curProfile: Profile = Profile(),
)