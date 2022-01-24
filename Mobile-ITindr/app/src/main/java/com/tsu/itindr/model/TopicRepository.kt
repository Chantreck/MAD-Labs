package com.tsu.itindr.model

import android.content.Context
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.retrofit.profile.TopicDto

class TopicRepository(context: Context) {
    private val topicDao = Database.getInstance(context).getTopicDao()

    fun getTopics() = topicDao.getTopics()

    suspend fun getTopic(title: String) = topicDao.getTopic(title)

    suspend fun saveSelectedTopics(topics: List<String>) {
        topicDao.saveSelectedTopics(topics)
    }

    suspend fun getTopicsCount() = topicDao.getTopicsCount()

    suspend fun saveTopics(topics: List<TopicDto>) {
        topicDao.saveTopics(topics.map { it.toEntity() } )
    }
}