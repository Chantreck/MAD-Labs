package com.tsu.itindr.people_fragment.model

import android.os.Parcelable
import com.tsu.itindr.model.database.users.UserEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
data class User(
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null,
    val topics: List<Topic>,
) : Parcelable

@Parcelize
data class Topic(
    val id: String,
    val title: String,
) : Parcelable