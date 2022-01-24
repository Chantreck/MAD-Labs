package com.tsu.itindr.model.database.topic

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsu.itindr.model.retrofit.profile.TopicDto

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val isSelected: Boolean
) {
    fun toDto() = TopicDto(
        id = id,
        title = title
    )
}