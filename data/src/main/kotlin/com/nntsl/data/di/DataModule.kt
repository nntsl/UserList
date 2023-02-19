package com.nntsl.data.di

import com.nntsl.data.database.datastore.UsersLocalDataSource
import com.nntsl.data.database.datastore.UsersLocalDataSourceImpl
import com.nntsl.data.network.UsersNetworkDataSource
import com.nntsl.data.network.retrofit.RetrofitUsersNetwork
import com.nntsl.data.repository.UsersRepositoryImpl
import com.nntsl.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUsersLocalDataSource(
        usersLocalDataSource: UsersLocalDataSourceImpl
    ): UsersLocalDataSource

    @Binds
    fun bindsUsersNetwork(
        network: RetrofitUsersNetwork
    ): UsersNetworkDataSource

    @Binds
    fun bindsUsersRepository(
        usersRepository: UsersRepositoryImpl
    ): UsersRepository
}
