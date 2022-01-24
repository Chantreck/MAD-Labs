package com.tsu.itindr.start_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.model.TopicRepository
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.retrofit.profile.TopicDto
import com.tsu.itindr.start_activity.model.StartController
import kotlinx.coroutines.launch

class StartViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = TopicRepository(app)
    private val controller = StartController()

    private val _isTokenValid = MutableLiveData<Boolean>()
    val isTokenValid: LiveData<Boolean>
        get() = _isTokenValid

    init {
        SharedPreferencesUtils.setup(app)
        checkToken()
    }

    private fun checkToken() {
        if (SharedPreferencesUtils.getAccessToken().isNotEmpty()) {
            try {
                controller.getTopics(
                    onSuccess = { topics ->
                        saveTopics(topics)
                        _isTokenValid.value = true
                    },
                    onFailure = {
                        _isTokenValid.value = false
                    }
                )
            } catch (e: Exception) {
                _isTokenValid.value = false
            }
        } else {
            _isTokenValid.value = false
        }
    }

    private fun saveTopics(topics: List<TopicDto>) {
        viewModelScope.launch {
            repository.saveTopics(topics)
        }
    }
}