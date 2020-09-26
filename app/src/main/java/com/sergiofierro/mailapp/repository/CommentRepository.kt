package com.sergiofierro.mailapp.repository

import androidx.annotation.WorkerThread
import com.sergiofierro.mailapp.model.Comment
import com.sergiofierro.mailapp.networking.CommentClient
import com.sergiofierro.mailapp.persistence.dao.CommentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepository @Inject constructor(
  private val commentClient: CommentClient,
  private val commentDao: CommentDao
) {

  @WorkerThread
  suspend fun fetchComments(postId: Int): Flow<List<Comment>> =
    flow {
      var comments = commentDao.getByPostId(postId)
      if (comments.isEmpty()) {
        comments = commentClient.fetchComments(postId)
        commentDao.insert(comments)
      }
      emit(comments)
    }.flowOn(Dispatchers.IO)
}
