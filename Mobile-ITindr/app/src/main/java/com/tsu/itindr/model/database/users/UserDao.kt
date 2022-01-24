package com.tsu.itindr.model.database.users

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.tsu.itindr.people_fragment.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<UserEntity>)

    @Query("UPDATE user SET avatar = :avatar WHERE userId = :userId")
    suspend fun updateProfilePicture(userId: String, avatar: String?)

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUser(userId: String): UserEntity

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getCurrentUser(userId: String): LiveData<UserEntity>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE userId = :userId)")
    suspend fun checkUserSaved(userId: String): Boolean

    @Query("SELECT * FROM user WHERE userId != :userId")
    fun observeAll(userId: String): LiveData<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM user WHERE userId != :userId")
    fun checkSavedUsers(userId: String): LiveData<Int>

    @Query("SELECT COUNT(*) FROM user WHERE userId != :userId")
    suspend fun getSavedUsersCount(userId: String): Int

    @Query("SELECT * FROM user WHERE userId != :userId")
    fun getUsers(userId: String): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user")
    suspend fun clearUsers()
}