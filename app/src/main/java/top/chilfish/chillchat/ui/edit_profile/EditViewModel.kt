package top.chilfish.chillchat.ui.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.repository.ContactsRepository
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
) : ViewModel() {
    private var _editState = MutableStateFlow(EditState())
    val editState: StateFlow<EditState> = _editState

    fun init(type: String) = viewModelScope.launch {
        async { contactsRepo.getUser() }.await().let { me ->
            _editState.update { it.copy(me = me, editType = type) }
        }
    }


    fun saveEdit() {

    }
}

data class EditState(
    val me: Profile? = null,
    val editType: String = "",
)