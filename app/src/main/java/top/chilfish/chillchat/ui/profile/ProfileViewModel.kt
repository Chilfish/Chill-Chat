package top.chilfish.chillchat.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val resStrProvider: ResStrProvider,
) : ViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    fun init(id: Long) = viewModelScope.launch {
        val profile = contactsRepo.getById(id) ?: return@launch
        _profileState.value = _profileState.value.copy(
            curProfile = profile,
        )
    }

    fun delContact(back: () -> Unit) = viewModelScope.launch {
        val res = contactsRepo.delete(_profileState.value.curProfile.id)

        showToast(
            resStrProvider.getString(
                if (res > 0) R.string.delete_success
                else R.string.delete_failed
            )
        )
        if (res > 0) back()
    }
}

data class ProfileState(
    val curProfile: Profile = Profile(),
)