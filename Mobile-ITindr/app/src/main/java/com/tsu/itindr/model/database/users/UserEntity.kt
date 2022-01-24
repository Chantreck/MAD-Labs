package com.tsu.itindr.model.database.users

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsu.itindr.model.retrofit.profile.UserDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null,
    val topics: String,
) {
    fun toDto() = UserDto(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar,
        topics = Json.decodeFromString(topics)
    )
}