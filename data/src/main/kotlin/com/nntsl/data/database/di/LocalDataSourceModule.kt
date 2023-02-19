package com.nntsl.data.database.di

import com.nntsl.data.database.datastore.UsersLocalDataSource
import com.nntsl.data.database.datastore.UsersLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {

//    @Binds
//    fun bindsUsersLocalDataSource(
//        usersLocalDataSource: UsersLocalDataSourceImpl
//    ): UsersLocalDataSource
}
