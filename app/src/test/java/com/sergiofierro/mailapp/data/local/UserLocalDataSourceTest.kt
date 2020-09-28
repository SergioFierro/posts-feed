package com.sergiofierro.mailapp.data.local

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.dao.UserDao
import com.sergiofierro.mailapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class UserLocalDataSourceTest {

  private val userDao: UserDao = mock()
  private val localDataSource = UserLocalDataSource(userDao, Dispatchers.Unconfined)


  @Test
  fun `Get user should return expected user`() {
    runBlocking {
      val id = 1
      val user: User = mock()
      whenever(userDao.get(id)).thenReturn(user)
      val result = localDataSource.get(id)
      assertTrue(result is Result.Success)
      assertEquals(user, (result as Result.Success).data)
    }
  }

  @Test
  fun `Get user should return error`() {
    runBlocking {
      val id = 1
      val expectedError = IOException()
      given(userDao.get(id)).willAnswer {
        throw expectedError
      }
      val result = localDataSource.get(id)
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }

  @Test
  fun `Save user should call dao`() {
    runBlocking {
      val user: User = mock()
      localDataSource.save(user)
      verify(userDao).insert(user)
    }
  }
}
