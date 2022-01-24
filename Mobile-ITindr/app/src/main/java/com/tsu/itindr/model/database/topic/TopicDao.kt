package com.tsu.itindr.model.database.topic

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TopicDao {
    @Query("SELECT COUNT(*) FROM topic")
    suspend fun getTopicsCount(): Int

    @Transaction
    suspend fun saveTopics(topics: List<TopicEntity>) {
        removeDifference(topics.map { it.id })
        addTopics(topics)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTopics(topics: List<TopicEntity>)

    @Query("DELETE FROM topic WHERE id not in (:topics)")
    suspend fun removeDifference(topics: List<String>)

    @Query("SELECT * FROM topic WHERE title = :title")
    suspend fun getTopic(title: String): TopicEntity

    @Query("SELECT * FROM topic")
    fun getTopics(): LiveData<List<TopicEntity>>

    @Transaction
    suspend fun saveSelectedTopics(titles: List<String>) {
        selectTopics(titles)
        deselectTopics(titles)
    }

    @Query("UPDATE topic SET isSelected = 1 where title in (:titles)")
    suspend fun selectTopics(titles: List<String>)

    @Query("UPDATE topic SET isSelected = 0 where title not in (:titles)")
    suspend fun deselectTopics(titles: List<String>)

    @Query("DELETE FROM topic")
    suspend fun clearTopics()
}