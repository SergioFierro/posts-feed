package com.sergiofierro.mailapp.repository.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.model.User
import com.sergiofierro.mailapp.repository.UserRepository
import kotlinx.coroutines.runBlocking

class UserRepositoryMock : UserRepository {

  var userServiceData: MutableList<User> = mutableListOf()
  private val observableUser = MutableLiveData<Result<User?>>()

  override suspend fun fetchUser(id: Int): Result<User?> {
    return Result.Success(userServiceData.find { it.id == id })
  }

  override fun observerUser(id: Int): LiveData<Result<User?>> {
    runBlocking { refreshUser(id) }
    return observableUser
  }

  private suspend fun refreshUser(id: Int) {
    observableUser.value = fetchUser(id)
  }

  fun addUsers(users: List<User>) {
    userServiceData.addAll(users)
  }
}
