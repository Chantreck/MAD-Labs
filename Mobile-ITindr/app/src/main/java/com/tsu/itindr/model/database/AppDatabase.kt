package com.tsu.itindr.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tsu.itindr.model.database.chat.ChatDao
import com.tsu.itindr.model.database.chat.ChatEntity
import com.tsu.itindr.model.database.messages.MessageDao
import com.tsu.itindr.model.database.messages.MessageEntity
import com.tsu.itindr.model.database.topic.TopicDao
import com.tsu.itindr.model.database.topic.TopicEntity
import com.tsu.itindr.model.database.users.UserDao
import com.tsu.itindr.model.database.users.UserEntity

@Database(entities = [UserEntity::class, TopicEntity::class, ChatEntity::class, MessageEntity::class], version = 5)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getPeopleDao(): UserDao
    abstract fun getTopicDao(): TopicDao
    abstract fun getChatDao(): ChatDao
    abstract fun getMessageDao(): MessageDao
}