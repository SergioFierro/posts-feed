package com.sergiofierro.mailapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.Result.Error
import com.sergiofierro.mailapp.data.local.UserLocalDataSource
import com.sergiofierro.mailapp.data.remote.UserRemoteDataSource
import com.sergiofierro.mailapp.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
  private val remoteDataSource: UserRemoteDataSource,
  private val localDataSource: UserLocalDataSource
) {

  @WorkerThread
  suspend fun fetchUser(id: Int): Result<User?> {
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

  fun observerUser(id: Int): LiveData<Result<User?>> = localDataSource.observeUser(id)
}
