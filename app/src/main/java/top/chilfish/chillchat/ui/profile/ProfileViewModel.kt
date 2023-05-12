package top.chilfish.chillchat.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.navigation.NavigationActions
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.provider.RepoProvider

class ProfileViewModel(
    private val uid: Long?,
    private val navHostController: NavHostController
) : ViewModel() {
    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: Flow<ProfileState> = _profileState
        .asStateFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        if (uid == null) return@launch
        val profileDeferred = async { RepoProvider.contactsRepo.getUser(uid) }
        val profile = profileDeferred.await() ?: return@launch

        _profileState.value = _profileState.value.copy(
            curProfile = profile,
        )
    }

    fun navToMessage() {
//        NavigationActions(navHostController).navigateTo(
//            route = Routers.Message,
//            id = uid,
//        )
    }
}

data class ProfileState(
    val curProfile: Profile = Profile(),
)