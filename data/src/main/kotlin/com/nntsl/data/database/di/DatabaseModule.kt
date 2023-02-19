package com.nntsl.data.database.di

import android.content.Context
import androidx.room.Room
import com.nntsl.data.database.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideSimpleChatDatabase(
        @ApplicationContext context: Context,
    ): UsersDatabase = Room.databaseBuilder(
        context,
        UsersDatabase::class.java,
        "users-database"
    ).build()
}
