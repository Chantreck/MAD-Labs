package com.tsu.itindr.sign_up_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.itindr.R
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.retrofit.refresh.LoginBody
import com.tsu.itindr.model.retrofit.refresh.LoginResponse
import com.tsu.itindr.sign_up_activity.model.RegisterController

class SignUpViewModel : ViewModel() {
    private val controller = RegisterController()

    private val _isEmailIncorrect = MutableLiveData<Int>()
    val isEmailIncorrect: LiveData<Int> get() = _isEmailIncorrect

    private val _isPasswordIncorrect = MutableLiveData<Int>()
    val isPasswordIncorrect: LiveData<Int> get() = _isPasswordIncorrect

    private val _isRepeatPasswordIncorrect = MutableLiveData<Int>()
    val isRepeatPasswordIncorrect: LiveData<Int> get() = _isRepeatPasswordIncorrect

    private val _registerResult = MutableLiveData<String?>()
    val registerResult: LiveData<String?>
        get() = _registerResult

    fun register(email: String, password: String, repeatPassword: String) {
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

        if (repeatPassword != password) {
            _isRepeatPasswordIncorrect.value = R.string.passwords_not_match
            return
        }
        _isRepeatPasswordIncorrect.value = 0

        signUp(email, password)
    }

    private fun signUp(email: String, password: String) {
        controller.register(
            LoginBody(email, password),
            onSuccess = fun(response: LoginResponse) {
                SharedPreferencesUtils.saveTokens(response)
                _registerResult.value = null
            },
            onFailure = fun(message: String) {
                _registerResult.value = message
            })
    }
}