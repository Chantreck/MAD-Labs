package com.tsu.itindr.model.database.messages

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM message WHERE chatId = :chatId ORDER BY createdAt DESC")
    fun getMessages(chatId: String): PagingSource<Int, MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessages(messages: List<MessageEntity>)

    @Query("DELETE FROM message")
    suspend fun clearMessages()

    @Query("SELECT COUNT(*) FROM message WHERE chatId = :chatId")
    suspend fun getSavedMessagesCount(chatId: String): Int
}