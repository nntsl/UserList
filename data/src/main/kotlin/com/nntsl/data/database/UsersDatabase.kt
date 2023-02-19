package com.nntsl.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nntsl.data.database.dao.UsersDao
import com.nntsl.data.database.model.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}
