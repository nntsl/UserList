package com.nntsl.data.database.di

import com.nntsl.data.database.UsersDatabase
import com.nntsl.data.database.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesUsersDao(
        database: UsersDatabase,
    ): UsersDao = database.usersDao()
}
