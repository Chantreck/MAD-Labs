package com.tsu.itindr.chats_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tsu.itindr.chat_activity.model.retrofit.ChatDto
import com.tsu.itindr.chats_fragment.model.ChatsController
import com.tsu.itindr.chats_fragment.model.ChatsRepository
import com.tsu.itindr.model.database.chat.ChatPreview
import kotlinx.coroutines.launch

class ChatsViewModel(app: Application): AndroidViewModel(app) {
    private val repository = ChatsRepository(app)
    private val controller = ChatsController()

    val chats: LiveData<List<ChatPreview>> = repository.getSavedChats()

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> get() = _errors

    init {
        getChats()
    }

    private fun getChats() {
        try {
            controller.getChats(
                onSuccess = {
                    addChatsToDb(it)
                },
                onFailure = fun(message: String) {
                    _errors.value = message
                })
        } catch (e: Exception) {
            _errors.value = e.message
        }
    }

    private fun addChatsToDb(chats: List<ChatDto>) {
        viewModelScope.launch {
            repository.addChats(chats.map { it.toEntity() })
        }
    }
}