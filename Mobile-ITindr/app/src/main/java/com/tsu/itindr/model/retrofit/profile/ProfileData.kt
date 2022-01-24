package com.tsu.itindr.model.retrofit.profile

import com.tsu.itindr.model.database.topic.TopicEntity
import com.tsu.itindr.model.database.users.UserEntity
import com.tsu.itindr.people_fragment.model.Topic
import com.tsu.itindr.people_fragment.model.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ProfileBody(
    val name: String,
    val aboutMyself: String? = null,
    val topics: List<String>? = null,
)

@Serializable
data class UserDto(
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null,
    val topics: List<TopicDto>,
) {
    fun toDomain() = User(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar,
        topics = topics.map { it.toDomain() }
    )

    fun toEntity() = UserEntity(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar,
        topics = Json.encodeToString(topics)
    )

    fun toProfileBody() = ProfileBody(
        name = name,
        aboutMyself = aboutMyself,
        topics = topics.map { it.id }
    )
}

@Serializable
data class TopicDto(
    val id: String,
    val title: String,
) {
    fun toDomain() = Topic(
        id = id,
        title = title
    )

    fun toEntity() = TopicEntity(
        id = id,
        title = title,
        isSelected = false
    )
}