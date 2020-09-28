package com.sergiofierro.posts_feed.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.Result.Success
import com.sergiofierro.posts_feed.data.dao.UserDao
import com.sergiofierro.posts_feed.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
  private val userDao: UserDao,
  private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

  suspend fun get(id: Int) = withContext(ioDispatcher) {
    return@withContext try {
      Success(userDao.get(id))
    } catch (e: Exception) {
      Result.Error(e)
    }
  }

  suspend fun save(user: User) {
    withContext(ioDispatcher) {
      userDao.insert(user)
    }
  }

  fun observeUser(id: Int): LiveData<Result<User?>> {
    return userDao.observeUser(id).map {
      Success(it)
    }
  }
}
