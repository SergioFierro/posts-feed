package com.sergiofierro.mailapp.data.remote

import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.Result.Success
import com.sergiofierro.mailapp.data.Result.Error
import com.sergiofierro.mailapp.model.Comment
import com.sergiofierro.mailapp.networking.CommentClient
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
