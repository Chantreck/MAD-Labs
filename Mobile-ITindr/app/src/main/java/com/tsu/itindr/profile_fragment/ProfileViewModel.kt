package com.tsu.itindr.profile_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.model.TopicRepository
import com.tsu.itindr.model.UserRepository
import com.tsu.itindr.model.database.users.UserEntity
import com.tsu.itindr.model.retrofit.profile.ProfileController
import com.tsu.itindr.model.retrofit.profile.UserDto
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.profile_fragment.model.Logout
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = UserRepository(app)
    private val topicRepository = TopicRepository(app)
    private val controller = ProfileController()
    private val logout = Logout(app)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loggedOut = MutableLiveData<Boolean>()
    val loggedOut: LiveData<Boolean> get() = _loggedOut

    val profile: LiveData<UserEntity> = repository.getCurrentUser()

    init {
        viewModelScope.launch {
            if (!repository.checkCurrentUser()) {
                getProfile()
            }
        }
    }

    private fun getProfile() {
        try {
            controller.getProfile(
                onSuccess = {
                    SharedPreferencesUtils.saveUserId(it.userId)
                    saveProfile(it)
                },
                onFailure = fun(message: String) {
                    _error.value = message
                }
            )
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    private fun saveProfile(user: UserDto) {
        val topics = user.topics.map { it.title }

        viewModelScope.launch {
            repository.addUser(user)
            topicRepository.saveSelectedTopics(topics)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logout.logout()
            _loggedOut.value = true
        }
    }
}