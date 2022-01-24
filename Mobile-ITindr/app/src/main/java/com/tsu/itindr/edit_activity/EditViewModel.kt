package com.tsu.itindr.edit_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.R
import com.tsu.itindr.model.TopicRepository
import com.tsu.itindr.model.UserRepository
import com.tsu.itindr.model.database.topic.TopicEntity
import com.tsu.itindr.model.database.users.UserEntity
import com.tsu.itindr.model.retrofit.profile.ProfileBody
import com.tsu.itindr.model.retrofit.profile.ProfileController
import com.tsu.itindr.model.retrofit.profile.UserDto
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditViewModel(app: Application): AndroidViewModel(app) {
    private val controller = ProfileController()
    private val repository = UserRepository(app)
    private val topicRepository = TopicRepository(app)

    val profile: LiveData<UserEntity> = repository.getCurrentUser()
    val topics: LiveData<List<TopicEntity>> = topicRepository.getTopics()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isNameCorrect = MutableLiveData<Int>()
    val isNameCorrect: LiveData<Int> get() = _isNameCorrect

    private val _changesSaved = MutableLiveData<Boolean>()
    val changesSaved: LiveData<Boolean> get() = _changesSaved

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

    fun saveProfileInfo(name: String, about: String, topics: List<String>) {
        if (name.isEmpty()) {
            _isNameCorrect.value = R.string.name_is_empty
            return
        } else {
            _isNameCorrect.value = 0
        }

        viewModelScope.launch {
            val topicsList = topics.map {
                topicRepository.getTopic(it).id
            }

            val updatedProfileBody = ProfileBody(name, about, topicsList)
            val profileBody = profile.value?.toDto()?.toProfileBody()

            if (profileBody != updatedProfileBody) {
                updateUser(updatedProfileBody)
            } else {
                _changesSaved.value = true
            }
        }
    }

    private fun updateUser(profile: ProfileBody) {
        try {
            controller.updateProfile(
                profile,
                onSuccess = {
                    saveProfile(it)
                    _changesSaved.value = true
                },
                onFailure = {
                    _error.value = it
                }
            )
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun uploadImage(image: MultipartBody.Part) {
        try {
            controller.updateProfilePicture(
                image,
                onSuccess = fun() {
                    getProfile()
                },
                onFailure = fun(message: String) {
                    _error.value = message
                }
            )
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    fun deleteImage() {
        try {
            controller.deleteProfilePicture(
                onSuccess = {
                    deleteProfilePicture()
                },
                onFailure = fun(message: String) {
                    _error.value = message
                }
            )
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    private fun deleteProfilePicture() {
        viewModelScope.launch {
            repository.updateProfilePicture(null)
        }
    }
}