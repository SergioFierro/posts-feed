package com.sergiofierro.posts_feed.data.remote

import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.networking.UserClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
  private val userClient: UserClient,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
  suspend fun get(id: Int) = withContext(ioDispatcher) {
    return@withContext try {
      Result.Success(userClient.fetchUser(id))
    } catch (e: Exception) {
      Result.Error(e)
    }
  }
}
