package com.nntsl.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nntsl.domain.model.UserInfo

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val avatar: String?,
    val source: String
)

fun UserEntity.asExternalModel() = UserInfo(
    id = id,
    name = name,
    avatar = avatar,
    source = source
)

fun UserInfo.asEntity() = UserEntity(
    id = id,
    name = name,
    avatar = avatar,
    source = source
)