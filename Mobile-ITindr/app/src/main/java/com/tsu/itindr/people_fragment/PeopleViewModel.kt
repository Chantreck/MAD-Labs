package com.tsu.itindr.people_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.people_fragment.model.PeopleRepository
import com.tsu.itindr.people_fragment.model.paging.PeopleRemoteMediator
import com.tsu.itindr.people_fragment.model.retrofit.PeopleController

class PeopleViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = PeopleRepository(app)
    private val controller = PeopleController()
    private val peopleDao = Database.getInstance(app).getPeopleDao()

    val savedUsersCount = repository.getSavedUsersCount()

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> get() = _errors

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        PagingConfig(12, initialLoadSize = 24),
        remoteMediator = PeopleRemoteMediator(app, controller)
    ) {
        val userId = SharedPreferencesUtils.getUserId()
        peopleDao.getUsers(userId)
    }
}