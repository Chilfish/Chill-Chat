package top.chilfish.chillchat.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.provider.RepoProvider
import top.chilfish.chillchat.provider.curUid

class MessageViewModel(
    private val profile: Profile,
) : ViewModel() {
    private val _messageState = MutableStateFlow(MessageState())
    val messageState: StateFlow<MessageState> = _messageState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        RepoProvider.messRepo.getAll(profile.id).collect { messages ->
            _messageState.update {
                it.copy(
                    messages = messages,
                    curProfile = profile
                )
            }
        }
    }

    fun more() {}

    fun sendMes(message: String) = viewModelScope.launch {
        val mes = Message(
            senderId = curUid,
            receiverId = profile.id,
            message = message,
            time = System.currentTimeMillis(),
        )
        launch { RepoProvider.chatsRepo.updateById(profile.id, mes.message, mes.time) }

        launch { RepoProvider.messRepo.insert(mes) }
    }
}

data class MessageState(
    val curProfile: Profile = Profile(),
    val messages: MutableList<Message> = mutableListOf(),
)