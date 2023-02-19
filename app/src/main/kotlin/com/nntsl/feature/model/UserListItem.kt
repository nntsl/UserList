package com.nntsl.feature.model

import com.nntsl.domain.model.UserInfo

data class UserListItem(
    val id: String,
    val name: String,
    val avatar: String?,
    val source: String
) : java.io.Serializable

fun List<UserInfo>.mapToUserScreenItems(): List<UserListItem> {
    return map { user ->
        UserListItem(
            id = user.id,
            name = user.name,
            avatar = user.avatar,
            source = user.source
        )
    }
}
