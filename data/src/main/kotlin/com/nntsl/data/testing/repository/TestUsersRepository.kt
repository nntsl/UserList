package com.nntsl.data.testing.repository

import com.nntsl.domain.model.UserInfo
import com.nntsl.domain.repository.UsersRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestUsersRepository : UsersRepository {

    private val usersFlow = MutableSharedFlow<List<UserInfo>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun getUsers(): Flow<List<UserInfo>> {
        return usersFlow
    }

    /**
     * A test-only API to allow controlling the list of users from tests.
     */
    fun sendUsers(users: List<UserInfo>) {
        usersFlow.tryEmit(users)
    }
}
