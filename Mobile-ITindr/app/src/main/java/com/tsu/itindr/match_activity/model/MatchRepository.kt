package com.tsu.itindr.match_activity.model

import android.content.Context
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.chat.ChatEntity

class MatchRepository(context: Context) {
    private val chatDao = Database.getInstance(context).getChatDao()

    suspend fun saveChat(chat: ChatEntity) {
        chatDao.addChat(chat)
    }
}