package com.tsu.itindr.model.database.chat

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChats(chats: List<ChatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChat(chat: ChatEntity)

    @Query("SELECT * FROM chat ORDER BY lastMessageDate DESC")
    fun getChats(): LiveData<List<ChatEntity>>

    @Query("DELETE FROM chat")
    suspend fun clearChats()

    @Query("SELECT * FROM chat WHERE id = :chatId")
    suspend fun getChat(chatId: String): ChatEntity
}