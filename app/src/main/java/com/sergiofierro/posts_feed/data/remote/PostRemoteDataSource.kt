package com.sergiofierro.posts_feed.data.remote

import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.model.Post
import com.sergiofierro.posts_feed.networking.PostClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
  private val postClient: PostClient,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

  suspend fun fetchPosts(): Result<List<Post>> = withContext(ioDispatcher) {
    return@withContext try {
      Result.Success(postClient.fetchPosts())
    } catch (e: Exception) {
      Result.Error(e)
    }
  }
}
