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
import top.chilfish.chillchat.provider.curCid
import top.chilfish.chillchat.provider.curId
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
                    .filter { user -> user.id != curId }
                    .toMutableList()
                )
            }
        }
    }

    fun search(id: String) = viewModelScope.launch {
        val res = contactsRepo.findUser(id) ?: return@launch
        Log.d("Chat", "Search: $res")

        _contactState.update {
            it.copy(
                searchRes = mutableListOf(res)
            )
        }
    }

    fun addContact(profile: Profile, back: () -> Unit) = viewModelScope.launch {
        if (contactsRepo.getById(profile.id) != null) {
            showToast(resStrProvider.getString(R.string.exist_contact))
            return@launch
        }

        val res = contactsRepo.add2Contact(profile)
        if (res) {
            showToast(resStrProvider.getString(R.string.add_contact_success))
            Log.d("Chat", "add: $profile")
            back()
        }
    }

    fun loadProfile(id: String) = viewModelScope.launch {
        val profile = contactsRepo.getById(id) ?: return@launch
        _contactState.update {
            it.copy(curProfile = profile)
        }
    }

    fun delContact(back: () -> Unit) = viewModelScope.launch {
        val chatterId = _contactState.value.curProfile.id
        val res = contactsRepo.delChatter(chatterId)

        showToast(
            resStrProvider.getString(
                if (res) R.string.delete_success
                else R.string.delete_failed
            )
        )
        if (res) back()
    }
}

data class ContactState(
    val contacts: MutableList<Profile> = mutableListOf(),

    val curProfile: Profile = Profile(),
    val searchRes: MutableList<Profile> = mutableListOf()
)