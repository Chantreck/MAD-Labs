package com.tsu.itindr.sign_in_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.itindr.R
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.retrofit.refresh.LoginBody
import com.tsu.itindr.model.retrofit.refresh.LoginResponse
import com.tsu.itindr.sign_in_activity.model.LoginController

class SignInViewModel : ViewModel() {
    private val controller = LoginController()

    private val _isEmailIncorrect = MutableLiveData<Int>()
    val isEmailIncorrect: LiveData<Int>
        get() = _isEmailIncorrect

    private val _isPasswordIncorrect = MutableLiveData<Int>()
    val isPasswordIncorrect: LiveData<Int>
        get() = _isPasswordIncorrect

    private val _loginResult = MutableLiveData<String?>()
    val loginResult: LiveData<String?>
        get() = _loginResult

    fun login(email: String, password: String) {
        if (email.isEmpty() || !email.contains("@")) {
            _isEmailIncorrect.value = R.string.email_incorrect
        } else {
            _isEmailIncorrect.value = 0
        }

        if (password.isEmpty()) {
            _isPasswordIncorrect.value = R.string.password_empty
            return
        } else if (password.length < 8) {
            _isPasswordIncorrect.value = R.string.password_short
            return
        }
        _isPasswordIncorrect.value = 0

        signIn(email, password)
    }

    private fun signIn(email: String, password: String) {
        controller.login(LoginBody(email, password),
            onSuccess = fun(response: LoginResponse) {
                SharedPreferencesUtils.saveTokens(response)
                _loginResult.value = null
            },
            onFailure = fun(message: String) {
                _loginResult.value = message
            })
    }
}