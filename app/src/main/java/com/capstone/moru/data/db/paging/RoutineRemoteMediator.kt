package com.capstone.moru.data.db.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.capstone.moru.data.api.retrofit.ApiService
import com.capstone.moru.data.db.model.RoutineModel
import com.capstone.moru.data.db.remote_key.RemoteKeys
import com.capstone.moru.data.db.user_routine.UserRoutineDatabase
import retrofit2.awaitResponse

@OptIn(ExperimentalPagingApi::class)
class RoutineRemoteMediator(
    private val userRoutineDatabase: UserRoutineDatabase,
    private val apiService: ApiService,
    private val token: String
) : RemoteMediator<Int, RoutineModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RoutineModel>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }
        return try {
            val response = apiService.getAllExercises(page = page, size = state.config.pageSize)
                .awaitResponse().body()

            val endOfPaginationReached = response?.list?.isEmpty()

            userRoutineDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userRoutineDatabase.remoteKeysDao().deleteAllRemoteKeys()
                    userRoutineDatabase.userRoutineDao().deleteAllUserRoutines()
                }

                val nextKey = if (endOfPaginationReached!!) null else page + 1
                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val keys = response.list.map {
                    RemoteKeys(
                        id = it?.id!!,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                userRoutineDatabase.remoteKeysDao().insertAllKey(keys)

                val responseData = response.list.map { item ->
                    RoutineModel(
                        item?.id!!,
                        item.title,
                        item.imgUrl,
                        item.type,
                        item.description,
                    )
                }

                userRoutineDatabase.userRoutineDao().insertUserRoutine(responseData)
            }





            MediatorResult.Success(endOfPaginationReached!!)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RoutineModel>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { data ->
            userRoutineDatabase.remoteKeysDao().getRemoteKey(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RoutineModel>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { data ->
            userRoutineDatabase.remoteKeysDao().getRemoteKey(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RoutineModel>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                userRoutineDatabase.remoteKeysDao().getRemoteKey(id)
            }
        }
    }


    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}