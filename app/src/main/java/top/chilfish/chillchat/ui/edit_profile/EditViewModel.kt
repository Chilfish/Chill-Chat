package top.chilfish.chillchat.ui.edit_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.navigation.EditType
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val userRepo: UserRepository,
    private val resStr: ResStrProvider,
) : ViewModel() {
    private var _editState = MutableStateFlow(EditState())
    val editState: StateFlow<EditState> = _editState

    private lateinit var navController: NavHostController

    fun init(type: String, navHostController: NavHostController) = viewModelScope.launch {
        navController = navHostController
        contactsRepo.getUser().collect { me ->
            _editState.update { it.copy(me = me, editType = type) }
        }
    }

    fun saveEdit(data: String, newPassword: String) = viewModelScope.launch {
        if (data == "") {
            navController.popBackStack()
            return@launch
        }

        if (newPassword != "") {
            editPassword(data, newPassword)
            return@launch
        }

        var profile = editState.value.me!!
        val type = editState.value.editType

        profile = profile.copy(
            nickname = if (type == EditType.Name) data else profile.nickname,
            bio = if (type == EditType.Bio) data else profile.bio,
            email = if (type == EditType.Email) data else profile.email,
        )

        val res = contactsRepo.update(profile)
        Log.d("Chat", "update: $res")

        if (res) {
            showToast(resStr.getString(R.string.update_success))
            _editState.update { it.copy(me = profile) }
            navController.popBackStack()
        } else {
            showToast(resStr.getString(R.string.update_failed))
        }
    }

    private fun editPassword(old: String, new: String) = viewModelScope.launch {
        val res = userRepo.updatePassword(old, new)
        if (res) {
            showToast(resStr.getString(R.string.update_success))
            navController.popBackStack()
        }
    }
}

data class EditState(
    val me: Profile? = null,
    val editType: String = "",
)