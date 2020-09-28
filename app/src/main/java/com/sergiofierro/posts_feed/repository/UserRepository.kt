package com.sergiofierro.posts_feed.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.Result.Error
import com.sergiofierro.posts_feed.data.local.UserLocalDataSource
import com.sergiofierro.posts_feed.data.remote.UserRemoteDataSource
import com.sergiofierro.posts_feed.model.User
import javax.inject.Inject

interface UserRepository {
  suspend fun fetchUser(id: Int): Result<User?>
  fun observerUser(id: Int): LiveData<Result<User?>>
}

class DefaultUserRepository @Inject constructor(
  private val remoteDataSource: UserRemoteDataSource,
  private val localDataSource: UserLocalDataSource
) : UserRepository {

  @WorkerThread
  override suspend fun fetchUser(id: Int): Result<User?> {
    try {
      val userResponse = remoteDataSource.get(id)
      if (userResponse is Result.Success) {
        localDataSource.save(userResponse.data)
      } else if (userResponse is Error) {
        throw userResponse.exception
      }
    } catch (ex: Exception) {
      return Error(ex)
    }
    return localDataSource.get(id)
  }

  override fun observerUser(id: Int): LiveData<Result<User?>> = localDataSource.observeUser(id)
}
