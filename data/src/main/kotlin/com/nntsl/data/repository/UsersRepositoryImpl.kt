package com.nntsl.data.repository

import com.nntsl.data.common.dispatchers.Dispatcher
import com.nntsl.data.common.dispatchers.UsersDispatchers
import com.nntsl.data.database.datastore.UsersLocalDataSource
import com.nntsl.data.network.UsersNetworkDataSource
import com.nntsl.data.network.retrofit.firstSource
import com.nntsl.data.network.retrofit.secondSource
import com.nntsl.domain.model.UserInfo
import com.nntsl.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsersNetworkDataSource,
    private val localDataSource: UsersLocalDataSource,
    @Dispatcher(UsersDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : UsersRepository {

    override suspend fun getUsers(): Flow<List<UserInfo>> {
        return getLocalUsers()
            .onEach { users ->
                if (users.isEmpty()) {
                    combine(getRemoteFirstUsers(), getRemoteSecondUsers()) { firstUsers, secondUsers ->
                        val newList = mutableListOf<UserInfo>()
                        newList.addAll(
                            firstUsers.list.map { user ->
                                UserInfo(
                                    id = user.id,
                                    name = user.name,
                                    avatar = null,
                                    source = firstSource
                                )
                            }
                        )

                        newList.addAll(
                            secondUsers.map { user ->
                                UserInfo(
                                    id = user.id.toString(),
                                    name = user.name ?: "",
                                    avatar = user.avatar,
                                    source = secondSource
                                )
                            }
                        )
                        newList
                    }.collect {
                        saveUsers(it)
                    }
                }
            }
    }

    private suspend fun getRemoteFirstUsers() = remoteDataSource.getFirstUsers()

    private suspend fun getRemoteSecondUsers() = remoteDataSource.getSecondUsers()

    private suspend fun saveUsers(
        list: List<UserInfo>
    ) {
        localDataSource.insertUsers(list)
    }

    private suspend fun getLocalUsers(): Flow<List<UserInfo>> {
        return localDataSource.getAllUsers().flowOn(dispatcher)
    }
}
