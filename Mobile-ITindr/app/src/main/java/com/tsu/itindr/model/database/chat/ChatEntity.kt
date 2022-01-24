package com.tsu.itindr.model.database.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val avatar: String? = null,
    val lastMessage: String? = null,
    val lastMessageDate: String? = null,
    val attachments: Boolean
) {
    fun toDomain() = ChatPreview(
        id = id,
        title = title,
        avatar = avatar,
        lastMessage = lastMessage,
        lastMessageDate = lastMessageDate,
        attachments = attachments
    )
}

data class ChatPreview(
    val id: String,
    val title: String,
    val avatar: String? = null,
    val lastMessage: String? = null,
    val lastMessageDate: String? = null,
    val attachments: Boolean
)