package com.tsu.itindr.match_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.chat_activity.model.retrofit.ChatInfo
import com.tsu.itindr.feed_fragment.model.FeedController
import com.tsu.itindr.match_activity.model.MatchRepository
import kotlinx.coroutines.launch

class MatchViewModel(app: Application) : AndroidViewModel(app) {
    private val controller = FeedController()
    private val repository = MatchRepository(app)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _chatId = MutableLiveData<String>()
    val chatId: LiveData<String> get() = _chatId

    fun createChat(userId: String) {
        try {
            controller.createChat(
                userId,
                onSuccess = fun(response: ChatInfo) {
                    _chatId.value = response.id
                    saveChat(response)
                },
                onFailure = fun(message: String) {
                    _error.value = message
                }
            )
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    private fun saveChat(chat: ChatInfo) {
        viewModelScope.launch {
            repository.saveChat(chat.toEntity())
        }
    }
}