package com.tsu.itindr.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.users.UserEntity
import com.tsu.itindr.model.retrofit.profile.UserDto
import com.tsu.itindr.model.utils.SharedPreferencesUtils

class UserRepository(context: Context) {
    private val userDao = Database.getInstance(context).getPeopleDao()
    private val userId by lazy { SharedPreferencesUtils.getUserId() }

    suspend fun getUser(userId: String) = userDao.getUser(userId).toDto().toDomain()

    fun getCurrentUser(): LiveData<UserEntity> {
        return userDao.getCurrentUser(userId)
    }

    suspend fun checkCurrentUser(): Boolean {
        return userDao.checkUserSaved(userId)
    }

    suspend fun addUser(user: UserDto) {
        userDao.addUser(user.toEntity())
    }

    suspend fun updateProfilePicture(avatar: String?) {
        userDao.updateProfilePicture(userId, avatar)
    }
}