package com.tsu.itindr.big_profile_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.model.UserRepository
import com.tsu.itindr.people_fragment.model.User
import kotlinx.coroutines.launch

class BigProfileViewModel(val app: Application, val userId: String) : AndroidViewModel(app) {
    private val repository = UserRepository(app)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun getUserFromRepository() {
        viewModelScope.launch {
            _user.value = repository.getUser(userId)
        }
    }
}