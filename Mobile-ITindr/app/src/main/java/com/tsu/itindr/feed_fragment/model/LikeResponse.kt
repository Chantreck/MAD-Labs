package com.tsu.itindr.feed_fragment.model

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val isMutual: Boolean,
)