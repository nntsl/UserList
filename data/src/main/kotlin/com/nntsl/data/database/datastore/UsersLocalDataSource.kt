package com.nntsl.data.database.datastore

import com.nntsl.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {

    suspend fun getAllUsers(): Flow<List<UserInfo>>

    suspend fun getUsersBySource(source: String): Flow<List<UserInfo>>

    suspend fun insertUsers(users: List<UserInfo>)
}
