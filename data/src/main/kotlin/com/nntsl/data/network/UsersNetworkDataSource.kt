package com.nntsl.data.network

import com.nntsl.data.network.model.SecondUserResponse
import com.nntsl.data.network.model.UserListResponse
import kotlinx.coroutines.flow.Flow

interface UsersNetworkDataSource {

    suspend fun getFirstUsers(): Flow<UserListResponse>

    suspend fun getSecondUsers(): Flow<List<SecondUserResponse>>
}