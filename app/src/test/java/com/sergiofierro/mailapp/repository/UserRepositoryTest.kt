package com.sergiofierro.mailapp.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.*
import com.sergiofierro.mailapp.model.User
import com.sergiofierro.mailapp.networking.UserClient
import com.sergiofierro.mailapp.persistence.dao.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class UserRepositoryTest {

  private val userClient: UserClient = mock()
  private val userDao: UserDao = mock()
  private val userRepository = UserRepository(userClient, userDao)

  @Test
  fun `Fetch user that is not stored should call api and return expected user`() {
    runBlocking {
      val userId = 123
      val expectedResponse: User = mock()
      whenever(userDao.get(userId)).thenReturn(null)
      whenever(userClient.fetchUser(userId)).thenReturn(expectedResponse)
      userRepository.fetchUser(userId).test {
        assertEquals(expectedResponse, expectItem())
        expectComplete()
      }
      verify(userDao).get(userId)
      verify(userClient).fetchUser(userId)
    }
  }

  @Test
  fun `Fetch comments from database should return expected items`() {
    runBlocking {
      val userId = 123
      val expectedResponse: User = mock()
      whenever(userDao.get(userId)).thenReturn(expectedResponse)
      userRepository.fetchUser(userId).test {
        assertEquals(expectedResponse, expectItem())
        expectComplete()
      }
      verify(userDao).get(userId)
      verify(userClient, never()).fetchUser(any())
    }
  }

  @InternalCoroutinesApi
  @Test
  fun `Fetch comments with exception should fail`() {
    runBlocking {
      val expectedError = IOException()
      given(userClient.fetchUser(any())).willAnswer {
        throw expectedError
      }
      userRepository.fetchUser(123).test {
        Assert.assertEquals(expectedError::class.java, expectError()::class.java)
      }
      verify(userClient).fetchUser(any())
    }
  }
}
