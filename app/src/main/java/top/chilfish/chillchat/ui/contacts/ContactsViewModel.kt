package top.chilfish.chillchat.ui.contacts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.provider.curUid
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val resStrProvider: ResStrProvider,
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

    fun search(id: Long) = viewModelScope.launch {
        val res = contactsRepo.findUser(id.toString()) ?: return@launch
        Log.d("Chat", "Search: $res")

        _contactState.update {
            it.copy(
                searchRes = mutableListOf(res)
            )
        }
    }

    fun addContact(profile: Profile) = viewModelScope.launch {
        if (contactsRepo.getById(profile.id) != null) {
            showToast(resStrProvider.getString(R.string.exist_contact))
            return@launch
        }
        showToast(resStrProvider.getString(R.string.add_contact_success))

        Log.d("Chat", "add: $profile")
        contactsRepo.insert(profile)
    }

    fun delContact(id: Long) = viewModelScope.launch {
        contactsRepo.delete(id)
    }
}

data class ContactState(
    val contacts: MutableList<Profile> = mutableListOf(),

    val searchRes: MutableList<Profile> = mutableListOf()
)