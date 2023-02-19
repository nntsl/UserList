package com.nntsl.data.database.dao

import androidx.room.*
import com.nntsl.data.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query(value = "SELECT * FROM users")
    fun getUsers(): Flow<List<UserEntity>>

    @Query(value = "SELECT * FROM users WHERE source = :source")
    fun getUsersBySource(source: String): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(entities: List<UserEntity>): List<Long>

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
