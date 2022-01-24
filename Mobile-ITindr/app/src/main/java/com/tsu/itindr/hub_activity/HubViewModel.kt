package com.tsu.itindr.hub_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.model.TopicRepository
import com.tsu.itindr.model.UserRepository
import com.tsu.itindr.model.retrofit.profile.ProfileController
import com.tsu.itindr.model.retrofit.profile.TopicDto
import com.tsu.itindr.model.retrofit.profile.UserDto
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.start_activity.model.StartController
import kotlinx.coroutines.launch

class HubViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = UserRepository(app)
    private val topicRepository = TopicRepository(app)
    private val controller = ProfileController()
    private val topicController = StartController()

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    init {
        checkTopics()
        checkProfile()
    }

    private fun checkTopics() {
        viewModelScope.launch {
            if (topicRepository.getTopicsCount() == 0) {
                try {
                    topicController.getTopics(
                        onSuccess = { topics ->
                            saveTopics(topics)
                        },
                        onFailure = {
                            _error.value = true
                        }
                    )
                } catch (e: Exception) {
                    _error.value = true
                }
            }
        }
    }

    private fun saveTopics(topics: List<TopicDto>) {
        viewModelScope.launch {
            topicRepository.saveTopics(topics)
        }
    }

    private fun checkProfile() {
        viewModelScope.launch {
            if (!repository.checkCurrentUser()) {
                try {
                    controller.getProfile(
                        onSuccess = {
                            SharedPreferencesUtils.saveUserId(it.userId)
                            saveProfile(it)
                        },
                        onFailure = fun(_: String) {
                            _error.value = true
                        }
                    )
                } catch (e: Exception) {
                    _error.value = true
                }
            }
        }
    }

    private fun saveProfile(user: UserDto) {
        val topics = user.topics.map { it.title }

        viewModelScope.launch {
            repository.addUser(user)
            topicRepository.saveSelectedTopics(topics)
        }
    }
}