package com.nntsl.data.database.datastore

import com.nntsl.data.common.dispatchers.Dispatcher
import com.nntsl.data.common.dispatchers.UsersDispatchers
import com.nntsl.data.database.dao.UsersDao
import com.nntsl.data.database.model.UserEntity
import com.nntsl.data.database.model.asEntity
import com.nntsl.data.database.model.asExternalModel
import com.nntsl.domain.model.UserInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersLocalDataSourceImpl @Inject constructor(
    private val dao: UsersDao,
    @Dispatcher(UsersDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : UsersLocalDataSource {

    override suspend fun getAllUsers(): Flow<List<UserInfo>> {
        return dao.getUsers()
            .flowOn(dispatcher)
            .catch { }
            .map { it.map(UserEntity::asExternalModel) }
    }

    override suspend fun getUsersBySource(source: String): Flow<List<UserInfo>> {
        return dao.getUsersBySource(source)
            .flowOn(dispatcher)
            .catch { }
            .map { it.map(UserEntity::asExternalModel) }
    }

    override suspend fun insertUsers(users: List<UserInfo>) {
        withContext(dispatcher) {
            dao.deleteAll()
            dao.insertUsers(users.map(UserInfo::asEntity))
            dao.getUsers()
        }
    }
}