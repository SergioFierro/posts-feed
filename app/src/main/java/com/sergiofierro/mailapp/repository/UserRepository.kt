package com.sergiofierro.mailapp.repository

import androidx.annotation.WorkerThread
import com.sergiofierro.mailapp.model.User
import com.sergiofierro.mailapp.networking.UserClient
import com.sergiofierro.mailapp.persistence.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
  private val userClient: UserClient,
  private val userDao: UserDao
) {

  @WorkerThread
  suspend fun fetchUser(id: Int): Flow<User?> =
    flow {
      var user = userDao.get(id)
      if (user == null) {
        user = userClient.fetchUser(id)
        userDao.insert(user)
      }
      emit(user)
    }.flowOn(Dispatchers.IO)
}
