package com.tsu.itindr.chat_activity.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.chat.ChatPreview
import com.tsu.itindr.model.database.messages.Message
import com.tsu.itindr.model.database.messages.MessageEntity

class ChatRepository(context: Context) {
    private val messageDao = Database.getInstance(context).getMessageDao()
    private val chatDao = Database.getInstance(context).getChatDao()

    suspend fun addMessage(message: MessageEntity) {
        messageDao.addMessage(message)
    }

    suspend fun getChat(chatId: String): ChatPreview = chatDao.getChat(chatId).toDomain()
}