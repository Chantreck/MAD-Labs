package com.tsu.itindr.feed_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsu.itindr.R
import com.tsu.itindr.feed_fragment.model.FeedController
import com.tsu.itindr.people_fragment.model.User

class FeedViewModel : ViewModel() {
    private val controller = FeedController()
    private var users: List<User> = listOf()

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private val _isPlaceholderShown = MutableLiveData<Boolean>()
    val isPlaceholderShown: LiveData<Boolean> get() = _isPlaceholderShown

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isMutual = MutableLiveData<Boolean>()
    val isMutual: LiveData<Boolean> get() = _isMutual

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> get() = _message

    init {
        if (users.isEmpty()) {
            getUsers()
        } else {
            _currentUser.value = users[0]
        }
    }

    private fun getUsers() {
        try {
            controller.getUsers(
                onSuccess = { list ->
                    users = list.map { it.toDomain() }
                    if (users.isNotEmpty()) {
                        _currentUser.value = users[0]
                    } else {
                        _isPlaceholderShown.value = true
                    }
                },
                onFailure = fun(message: String) {
                    _errorMessage.value = message
                }
            )
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun likeUser(user: User) {
        try {
            controller.likeUser(
                user.userId,
                onMutual = {
                    _isMutual.value = true
                },
                onSuccess = {
                    _message.value = R.string.liked
                    removeUser()
                },
                onFailure = fun(message: String) {
                    _errorMessage.value = message
                }
            )
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun dislikeUser(user: User) {
        try {
            controller.dislikeUser(
                user.userId,
                onSuccess = {
                    _message.value = R.string.disliked
                    removeUser()
                },
                onFailure = fun(message: String) {
                    _errorMessage.value = message
                }
            )
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    private fun removeUser() {
        users = users.drop(1)
        if (users.isEmpty()) {
            getUsers()
            return
        } else {
            _currentUser.value = users[0]
        }
    }
}