package com.sergiofierro.mailapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.Result.Error
import com.sergiofierro.mailapp.data.local.CommentLocalDataSource
import com.sergiofierro.mailapp.data.remote.CommentRemoteDataSource
import com.sergiofierro.mailapp.model.Comment
import javax.inject.Inject

class CommentRepository @Inject constructor(
  private val remoteDataSource: CommentRemoteDataSource,
  private val localDataSource: CommentLocalDataSource
) {

  @WorkerThread
  suspend fun fetchComments(postId: Int): Result<List<Comment>> {
    try {
      val commentsResponse = remoteDataSource.fetchComments(postId)
      if (commentsResponse is Result.Success) {
        localDataSource.save(commentsResponse.data)
      } else if (commentsResponse is Error) {
        throw commentsResponse.exception
      }
    } catch (ex: Exception) {
      return Error(ex)
    }
    return localDataSource.getByPostId(postId)
  }

  fun observerComments(postId: Int): LiveData<Result<List<Comment>>> = localDataSource.observeComments(postId)
}
