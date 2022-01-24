package com.tsu.itindr.chat_activity.model.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tsu.itindr.chat_activity.model.retrofit.ChatController
import com.tsu.itindr.model.database.Database
import com.tsu.itindr.model.database.messages.MessageEntity
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class ChatRemoteMediator(
    context: Context,
    private val chatId: String,
    private val controller: ChatController,
) :
    RemoteMediator<Int, MessageEntity>() {
    private val database = Database.getInstance(context)
    private val messageDao = database.getMessageDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>,
    ): MediatorResult {
        try {
            val offset = when (loadType) {
                LoadType.REFRESH -> return MediatorResult.Success(endOfPaginationReached = false)
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    messageDao.getSavedMessagesCount(chatId)
                }
            }

            val response = controller.getMessages(chatId, state.config.pageSize, offset)

            val messages = response.body() ?: return MediatorResult.Error(HttpException(response))

            database.withTransaction {
                messageDao.addMessages(messages.map { it.toEntity(chatId) })
            }

            return MediatorResult.Success(endOfPaginationReached = messages.size < state.config.pageSize)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}