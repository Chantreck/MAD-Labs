package com.tsu.itindr.user_profile_activity

import android.app.Application
import androidx.lifecycle.*
import com.tsu.itindr.R
import com.tsu.itindr.feed_fragment.model.FeedController
import com.tsu.itindr.people_fragment.model.User
import com.tsu.itindr.model.UserRepository
import kotlinx.coroutines.launch

class UserProfileViewModel(val app: Application, private val userId: String) : AndroidViewModel(app) {
    private val controller = FeedController()
    private val repository = UserRepository(app)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isMutual = MutableLiveData<Boolean>()
    val isMutual: LiveData<Boolean> get() = _isMutual

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> get() = _message

    init {
        viewModelScope.launch {
            _user.value = repository.getUser(userId)
        }
    }

    fun likeUser() {
        try {
            controller.likeUser(
                userId,
                onMutual = {
                    _isMutual.value = true
                },
                onSuccess = {
                    _message.value = R.string.liked
                },
                onFailure = fun(message: String) {
                    _errorMessage.value = message
                }
            )
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun dislikeUser() {
        try {
            controller.dislikeUser(
                userId,
                onSuccess = {
                    _message.value = R.string.disliked
                },
                onFailure = fun(message: String) {
                    _errorMessage.value = message
                }
            )
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }
}