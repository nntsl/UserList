package com.nntsl.domain.repository

import com.nntsl.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUsers(): Flow<List<UserInfo>>
}
