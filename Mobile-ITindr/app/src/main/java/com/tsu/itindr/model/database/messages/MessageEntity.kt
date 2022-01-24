package com.tsu.itindr.model.database.messages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val text: String? = null,
    val createdAt: String,
    val senderId: String,
    val senderAvatar: String? = null,
    val attachments: String? = null,
    val chatId: String
) {
    fun toDomain() = Message(
        id = id,
        text = text,
        createdAt = createdAt,
        senderId = senderId,
        senderAvatar = senderAvatar,
        attachments = attachments,
    )
}

data class Message(
    val id: String,
    val text: String? = null,
    val createdAt: String,
    val senderId: String,
    val senderAvatar: String? = null,
    val attachments: String? = null
)
