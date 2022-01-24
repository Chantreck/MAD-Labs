package com.tsu.itindr.chat_activity.model.retrofit

import com.tsu.itindr.model.database.chat.ChatEntity
import com.tsu.itindr.model.database.messages.MessageEntity
import kotlinx.serialization.Serializable

@Serializable
data class ChatInfo(
    val id: String,
    val title: String,
    val avatar: String? = null,
) {
    fun toEntity() = ChatEntity(
        id = id,
        title = title,
        avatar = avatar,
        lastMessage = null,
        lastMessageDate = null,
        attachments = false
    )
}

@Serializable
data class ChatDto(
    val chat: ChatInfo,
    val lastMessage: MessageDto? = null,
) {
    fun toEntity() = ChatEntity(
        id = chat.id,
        title = chat.title,
        avatar = chat.avatar,
        lastMessage = lastMessage?.text,
        lastMessageDate = lastMessage?.createdAt,
        attachments = lastMessage?.attachments?.isNotEmpty() ?: false
    )
}

@Serializable
data class MessageDto(
    val id: String,
    val text: String? = null,
    val createdAt: String,
    val user: Sender,
    val attachments: List<String>? = null,
) {
    fun toEntity(chatId: String) = if (attachments.isNullOrEmpty()) {
        MessageEntity(id, text, createdAt, user.userId, user.avatar, null, chatId)
    } else {
        MessageEntity(id, text, createdAt, user.userId, user.avatar, attachments[0], chatId)
    }
}

@Serializable
data class Sender(
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null,
)