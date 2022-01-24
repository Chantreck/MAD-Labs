package com.tsu.itindr.profile_fragment.model

import android.content.Context
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.utils.SharedPreferencesUtils

class Logout(context: Context) {
    private val db = Database.getInstance(context)
    private val topicsDao = db.getTopicDao()
    private val peopleDao = db.getPeopleDao()
    private val chatDao = db.getChatDao()
    private val messageDao = db.getMessageDao()

    suspend fun logout() {
        chatDao.clearChats()
        messageDao.clearMessages()
        topicsDao.clearTopics()
        peopleDao.clearUsers()
        SharedPreferencesUtils.clearInfo()
    }
}