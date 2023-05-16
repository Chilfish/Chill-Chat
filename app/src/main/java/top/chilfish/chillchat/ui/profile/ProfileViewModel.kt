package top.chilfish.chillchat.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ContactsRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
) : ViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: Flow<ProfileState> = _profileState
        .asStateFlow()

    fun init(id: Long) = viewModelScope.launch {
        val profile = contactsRepo.getById(id) ?: return@launch
        _profileState.value = _profileState.value.copy(
            curProfile = profile,
        )
    }

    fun more() {

    }
}

data class ProfileState(
    val curProfile: Profile = Profile(),
)