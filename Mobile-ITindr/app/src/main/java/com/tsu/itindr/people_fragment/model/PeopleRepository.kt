package com.tsu.itindr.people_fragment.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.users.UserEntity

class PeopleRepository(context: Context) {
    private val peopleDao = Database.getInstance(context).getPeopleDao()
    private val userId by lazy { SharedPreferencesUtils.getUserId() }

    fun getSavedUsersCount(): LiveData<Int> = peopleDao.checkSavedUsers(userId)
}