package com.sergiofierro.mailapp.repository

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.local.UserLocalDataSource
import com.sergiofierro.mailapp.data.remote.UserRemoteDataSource
import com.sergiofierro.mailapp.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class UserRepositoryTest {

  private val remoteDataSource: UserRemoteDataSource = mock()
  private val localDataSource: UserLocalDataSource = mock()
  private val repository = UserRepository(remoteDataSource, localDataSource)

  @Test
  fun `Fetch user by id should call api and return expected user`() {
    runBlocking {
      val id = 123
      val user: User = mock()
      whenever(remoteDataSource.get(id)).thenReturn(Result.Success(user))
      whenever(localDataSource.get(id)).thenReturn(Result.Success(user))
      val result = repository.fetchUser(id)
      verify(localDataSource).save(user)
      Assert.assertTrue(result is Result.Success)
      assertEquals(user, (result as Result.Success).data)
    }
  }

  @Test
  fun `Fetch user by id should return error`() {
    runBlocking {
      val id = 123
      val expectedError = IOException()
      given(remoteDataSource.get(id)).willAnswer {
        throw expectedError
      }
      val result = repository.fetchUser(id)
      Assert.assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }
}
