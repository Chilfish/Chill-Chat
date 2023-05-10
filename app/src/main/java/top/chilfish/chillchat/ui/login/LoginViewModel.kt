package top.chilfish.chillchat.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.utils.showToast

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState


    fun goToLogin() = viewModelScope.launch {
        if (!checkSubmit()) return@launch

        // TODO: request to server
        success()
    }

    fun goToRegister() = viewModelScope.launch {
        if (!checkSubmit()) return@launch

        // TODO: request to server
        success()
    }

    private fun success() = viewModelScope.launch {
        AccountProvider.setLogin(_loginState.value.username)
        _loginState.value = _loginState.value.copy(isLoginSuccess = true)
    }

    private fun checkSubmit(): Boolean {
        if (_loginState.value.isUsernameError
            || _loginState.value.isPasswordError
            || _loginState.value.username.isEmpty()
            || _loginState.value.password.isEmpty()
        ) {
            showToast("Please check your input")
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
        _loginState.value = _loginState.value.copy(
            isUsernameError = isError, username = usn
        )
    }

    private fun setPswError(isError: Boolean, psw: String) {
        _loginState.value = _loginState.value.copy(
            isPasswordError = isError, password = psw
        )
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