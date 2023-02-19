package com.nntsl.data.database.datastore

import com.nntsl.data.common.dispatchers.Dispatcher
import com.nntsl.data.common.dispatchers.UsersDispatchers
import com.nntsl.data.database.dao.UsersDao
import com.nntsl.data.database.model.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersDataStore @Inject constructor(
    private val dao: UsersDao,
    @Dispatcher(UsersDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    suspend fun getUsers(): Flow<List<UserEntity>> = withContext(dispatcher) {
        dao.getUsers()
    }

    suspend fun getUsersBySource(source: String): Flow<List<UserEntity>> = withContext(dispatcher) {
        dao.getUsersBySource(source)
    }

    suspend fun saveUsers(list: List<UserEntity>): Flow<List<UserEntity>> =
        withContext(dispatcher) {
            dao.deleteAll()
            dao.insertUsers(list)
            dao.getUsers()
        }
}
