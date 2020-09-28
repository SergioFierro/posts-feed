package com.sergiofierro.posts_feed.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.Result.Error
import com.sergiofierro.posts_feed.data.Result.Success
import com.sergiofierro.posts_feed.data.dao.PostDao
import com.sergiofierro.posts_feed.model.Post
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostLocalDataSource @Inject constructor(
  private val postDao: PostDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

  suspend fun getAll(): Result<List<Post>> = withContext(ioDispatcher) {
    return@withContext try {
      Success(postDao.getAll())
    } catch (e: Exception) {
      Error(e)
    }
  }

  suspend fun save(post: Post) = withContext(ioDispatcher) {
    postDao.insert(post)
  }

  suspend fun save(posts: List<Post>) = withContext(ioDispatcher) {
    postDao.insert(posts)
  }

  suspend fun delete(post: Post) = withContext(ioDispatcher) {
    postDao.delete(post)
  }

  suspend fun deleteAll() = withContext(ioDispatcher) {
    postDao.deleteAll()
  }

  fun observePosts(): LiveData<Result<List<Post>>> {
    return postDao.observePosts().map {
      Success(it)
    }
  }
}
