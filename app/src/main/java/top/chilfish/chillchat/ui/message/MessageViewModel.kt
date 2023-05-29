package top.chilfish.chillchat.ui.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository
import top.chilfish.chillchat.provider.curCid
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messRepo: MessageRepository,
    private val chatsRepo: ChatsListRepository,
    private val contactsRepo: ContactsRepository,
) : ViewModel() {
    private val _messageState = MutableStateFlow(MessageState())
    val messageState: StateFlow<MessageState> = _messageState

    fun init(chatterId: String) = viewModelScope.launch {
        launch {
            messRepo.getAll(chatterId).collect { messages ->
                _messageState.update {
                    it.copy(messages = messages)
                }
            }
        }

        Log.d("Chat", "all mes: ${messageState.value.messages}")

        launch {
            val chatter = contactsRepo.getById(chatterId) ?: Profile()
            _messageState.update { it.copy(chatter = chatter) }
        }
    }

    fun more() {}

    fun sendMes(message: String) = viewModelScope.launch {
        val chatter = messageState.value.chatter
        val mes = messRepo.sendMes(chatter.id, message)

        launch { chatsRepo.updateById(chatter.id, mes.message, mes.time) }
        launch { messRepo.insert(mes) }
    }
}

data class MessageState(
    val chatter: Profile = Profile(),
    val messages: MutableList<Message> = mutableListOf(),
)