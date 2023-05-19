package top.chilfish.chillchat.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.provider.curUid
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
) : ViewModel() {
    private val _contactState = MutableStateFlow(ContactState())
    val contactState: StateFlow<ContactState> = _contactState

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        contactsRepo.allUsers().collect { users ->
            _contactState.update {
                it.copy(contacts = users
                    .filter { user -> user.id != curUid }
                    .toMutableList()
                )
            }
        }
    }

    fun addContact(profile: Profile) {

    }

    fun delContact(id: Long) {

    }
}

data class ContactState(
    val contacts: MutableList<Profile> = mutableListOf()
)