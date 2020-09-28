package com.sergiofierro.posts_feed.data.remote

import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.Result.Success
import com.sergiofierro.posts_feed.data.Result.Error
import com.sergiofierro.posts_feed.model.Comment
import com.sergiofierro.posts_feed.networking.CommentClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
  private val commentClient: CommentClient,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

  suspend fun fetchComments(postId: Int): Result<List<Comment>> = withContext(ioDispatcher) {
    return@withContext try {
      Success(commentClient.fetchComments(postId))
    } catch (e: Exception) {
      Error(e)
    }
  }
}
