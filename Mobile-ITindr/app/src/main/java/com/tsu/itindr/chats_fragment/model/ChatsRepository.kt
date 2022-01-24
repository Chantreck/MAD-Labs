package com.tsu.itindr.chats_fragment.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.chat.ChatEntity
import com.tsu.itindr.model.database.chat.ChatPreview

class ChatsRepository(context: Context) {
    private val chatsDao = Database.getInstance(context).getChatDao()

    fun getSavedChats(): LiveData<List<ChatPreview>> =
        chatsDao.getChats().map { list -> list.map { it.toDomain() } }

    suspend fun addChats(chats: List<ChatEntity>) {
        chatsDao.addChats(chats)
    }
}