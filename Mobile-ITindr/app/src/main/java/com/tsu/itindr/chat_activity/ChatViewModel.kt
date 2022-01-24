package com.tsu.itindr.chat_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tsu.itindr.chat_activity.model.retrofit.ChatController
import com.tsu.itindr.chat_activity.model.ChatRepository
import com.tsu.itindr.chat_activity.model.paging.ChatRemoteMediator
import com.tsu.itindr.chat_activity.model.retrofit.MessageDto
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.chat.ChatPreview
import com.tsu.itindr.model.database.messages.Message
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ChatViewModel(app: Application, private val chatId: String) : AndroidViewModel(app) {
    private val repository = ChatRepository(app)
    private val controller = ChatController()
    private val messageDao = Database.getInstance(app).getMessageDao()

    private val _chat = MutableLiveData<ChatPreview>()
    val chat: LiveData<ChatPreview> get() = _chat

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> get() = _errors

    fun getChatInfo() {
        viewModelScope.launch {
            _chat.value = repository.getChat(chatId)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        PagingConfig(5, initialLoadSize = 10),
        remoteMediator = ChatRemoteMediator(app, chatId, controller)
    ) {
        messageDao.getMessages(chatId)
    }

    fun sendMessage(text: String, imagePart: RequestBody?) {
        val messageText = if (text.isBlank()) {
            null
        } else {
            text.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        }

        if (messageText == null && imagePart == null) {
            _errors.value = "Сообщение не может быть пустым"
            return
        }

        try {
            controller.sendMessage(
                chatId,
                messageText,
                imagePart,
                onSuccess = {
                    addMessage(it)
                },
                onFailure = fun(message: String) {
                    _errors.value = message
                }
            )
        } catch (e: Exception) {
            _errors.value = e.message
        }
    }

    private fun addMessage(message: MessageDto) {
        viewModelScope.launch {
            repository.addMessage(message.toEntity(chatId))
        }
    }
}