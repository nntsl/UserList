package com.nntsl.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserListResponse(
    val page: Int,
    val limit: Int,
    val explicit: Boolean,
    val total: Int,
    @SerialName("has_more")
    val hasMore: Boolean,
    val list: List<FirstUserResponse>
)

@Serializable
class FirstUserResponse(
    val id: String,
    @SerialName("screenname")
    val name: String
)
