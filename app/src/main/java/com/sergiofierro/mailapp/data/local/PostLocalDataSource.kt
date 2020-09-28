package com.sergiofierro.mailapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.Result.Error
import com.sergiofierro.mailapp.data.Result.Success
import com.sergiofierro.mailapp.data.dao.PostDao
import com.sergiofierro.mailapp.model.Post
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
