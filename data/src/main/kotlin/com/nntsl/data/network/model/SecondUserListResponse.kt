package com.nntsl.data.network.model

import kotlinx.serialization.*

@Serializable
data class SecondSourceUserResponse(
    val url: String
)

@Serializable
class SecondUserResponse(
    val id: Int,
    val name: String?,
    @SerialName("avatar_url")
    val avatar: String
)