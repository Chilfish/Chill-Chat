package top.chilfish.chillchat.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.module.IODispatcher
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messRepo: MessageRepository,
    private val contactsRepo: ContactsRepository,

    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _messageState = MutableStateFlow(MessageState())
    val messageState: StateFlow<MessageState> = _messageState

    fun init(chatterId: String) = viewModelScope.launch {
        messRepo.isRead()

        launch(ioDispatcher) {
            messRepo.getAll(chatterId).collect { messages ->
                _messageState.update {
                    it.copy(messages = messages)
                }
            }
        }

        launch(ioDispatcher) {
            val chatter = contactsRepo.getById(chatterId) ?: Profile()
            _messageState.update { it.copy(chatter = chatter) }
        }
    }

    fun more() {}

    fun sendMes(message: String) = viewModelScope.launch {
        val chatter = messageState.value.chatter
        messRepo.sendMes(chatter.id, message)
    }
}

data class MessageState(
    val chatter: Profile = Profile(),
    val messages: MutableList<Message> = mutableListOf(),
)