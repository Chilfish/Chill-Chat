package top.chilfish.chillchat.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile

class ProfileViewModel(
    private val profile: Profile,
) : ViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: Flow<ProfileState> = _profileState
        .asStateFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
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