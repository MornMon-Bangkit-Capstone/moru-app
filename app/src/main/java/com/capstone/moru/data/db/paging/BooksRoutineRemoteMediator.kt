package com.capstone.moru.data.db.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.capstone.moru.data.api.retrofit.ApiService
import com.capstone.moru.data.db.model.BooksRoutineModel
import com.capstone.moru.data.db.model.ExerciseRoutineModel
import com.capstone.moru.data.db.remote_key.BooksRemoteKeys
import com.capstone.moru.data.db.remote_key.ExerciseRemoteKey
import com.capstone.moru.data.db.user_routine.UserRoutineDatabase
import retrofit2.awaitResponse

@OptIn(ExperimentalPagingApi::class)
class BooksRoutineRemoteMediator(
    private val userRoutineDatabase: UserRoutineDatabase,
    private val apiService: ApiService,
    private val token: String
) : RemoteMediator<Int, BooksRoutineModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BooksRoutineModel>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getBooksRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getBooksRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getBooksRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }
        return try {
            val response =
                apiService.getAllBooks(page = page, size = state.config.pageSize).awaitResponse()
                    .body()

            val endOfPaginationReached = response?.list?.isEmpty()

            userRoutineDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userRoutineDatabase.remoteKeysDao().deleteAllBooksRemoteKeys()
                    userRoutineDatabase.userRoutineDao().deleteAllUserBooksRoutines()
                }

                val nextKey = if (endOfPaginationReached!!) null else page + 1
                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val keys = response.list.map {
                    BooksRemoteKeys(
                        id = it?.id!!,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                userRoutineDatabase.remoteKeysDao().insertAllBooksKey(keys)

                val responseData = response.list.map { item ->
                    BooksRoutineModel(
                        item?.id!!,
                        item.title,
                        item.imgUrl,
                        item.type,
                        item.description,
                    )
                }

                userRoutineDatabase.userRoutineDao().insertUserBooksRoutine(responseData)

            }


            MediatorResult.Success(endOfPaginationReached!!)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getBooksRemoteKeyForLastItem(state: PagingState<Int, BooksRoutineModel>): BooksRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { data ->
            userRoutineDatabase.remoteKeysDao().getBooksRemoteKey(data.id)
        }
    }

    private suspend fun getBooksRemoteKeyForFirstItem(state: PagingState<Int, BooksRoutineModel>): BooksRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { data ->
            userRoutineDatabase.remoteKeysDao().getBooksRemoteKey(data.id)
        }
    }

    private suspend fun getBooksRemoteKeyClosestToCurrentPosition(state: PagingState<Int, BooksRoutineModel>): BooksRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                userRoutineDatabase.remoteKeysDao().getBooksRemoteKey(id)
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}