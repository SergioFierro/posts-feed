package com.sergiofierro.posts_feed.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.Result.Success
import com.sergiofierro.posts_feed.data.Result.Error
import com.sergiofierro.posts_feed.data.dao.CommentDao
import com.sergiofierro.posts_feed.model.Comment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentLocalDataSource @Inject constructor(
  private val commentDao: CommentDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

  suspend fun getByPostId(postId: Int): Result<List<Comment>> = withContext(ioDispatcher) {
    return@withContext try {
      Success(commentDao.getByPostId(postId))
    } catch (e: Exception) {
      Error(e)
    }
  }

  suspend fun save(comments: List<Comment>) = withContext(ioDispatcher) {
    commentDao.insert(comments)
  }

  fun observeComments(postId: Int): LiveData<Result<List<Comment>>> {
    return commentDao.observeComments(postId).map {
      Success(it)
    }
  }
}
