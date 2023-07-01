package top.chilfish.chillchat.ui.login

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
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.ResStrProvider
import top.chilfish.chillchat.utils.showToast
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val contactsRepo: ContactsRepository,
    private val resStr: ResStrProvider,
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun goToLogin() = viewModelScope.launch {
        if (!checkSubmit()) return@launch

        val (username, password) = loginState.value
        val res = userRepo.auth(username, password)
        if (res != null) {
            success(res)
        }
    }

    fun goToRegister() = viewModelScope.launch {
        if (!checkSubmit()) return@launch

        val (username, password) = loginState.value
        val res = userRepo.auth(username, password, false)
        if (res != null) {
            success(res)
        }
    }

    private fun success(me: Profile) = viewModelScope.launch {
        AccountProvider.setLogin(me.id, me.cid)
        contactsRepo.setUser(me)
        _loginState.update {
            it.copy(isLoginSuccess = true)
        }
    }

    private fun checkSubmit(): Boolean {
        if (_loginState.value.isUsernameError
            || _loginState.value.isPasswordError
            || _loginState.value.username.isEmpty()
            || _loginState.value.password.isEmpty()
        ) {
            showToast(resStr.getString(R.string.check_input))
            return false
        }
        return true
    }

    fun checkUsername(usn: String) {
        if (usn.length >= MAX_USN) {
            setUsnError(true, usn)
        } else {
            setUsnError(false, usn)
        }
    }

    fun checkPassword(psw: String) {
        if (psw.length < MIN_PAW) {
            setPswError(true, psw)
        } else {
            setPswError(false, psw)
        }
    }

    private fun setUsnError(isError: Boolean, usn: String) {
        _loginState.update {
            it.copy(
                isUsernameError = isError,
                username = usn
            )
        }
    }

    private fun setPswError(isError: Boolean, psw: String) {
        _loginState.update {
            it.copy(
                isPasswordError = isError,
                password = psw
            )
        }
    }
}

data class LoginState(
    val username: String = "",
    val password: String = "",

    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,

    val isLoginSuccess: Boolean = false,
)

const val MIN_PAW = 8
const val MAX_USN = 20