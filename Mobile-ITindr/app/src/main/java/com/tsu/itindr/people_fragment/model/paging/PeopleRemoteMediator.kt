package com.tsu.itindr.people_fragment.model.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.users.UserEntity
import com.tsu.itindr.people_fragment.model.retrofit.PeopleController
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PeopleRemoteMediator(context: Context, private val controller: PeopleController) :
    RemoteMediator<Int, UserEntity>() {
    private val database = Database.getInstance(context)
    private val peopleDao = database.getPeopleDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>,
    ): MediatorResult {
        try {
            val offset = when (loadType) {
                LoadType.REFRESH -> return MediatorResult.Success(endOfPaginationReached = false)
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val userId = SharedPreferencesUtils.getUserId()
                    peopleDao.getSavedUsersCount(userId)
                }
            }

            val response = controller.getUsers(state.config.pageSize, offset)

            val users = response.body() ?: return MediatorResult.Error(HttpException(response))

            database.withTransaction {
                peopleDao.addUsers(users.map { it.toEntity() })
            }

            return MediatorResult.Success(endOfPaginationReached = users.size < state.config.pageSize)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}