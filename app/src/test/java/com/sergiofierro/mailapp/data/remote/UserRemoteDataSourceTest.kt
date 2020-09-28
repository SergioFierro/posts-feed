package com.sergiofierro.mailapp.data.remote

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.model.User
import com.sergiofierro.mailapp.networking.UserClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class UserRemoteDataSourceTest {

  private val client: UserClient = mock()
  private val remoteDataSource = UserRemoteDataSource(client, Dispatchers.Unconfined)

  @Test
  fun `Fetch user by id should return expected user`() {
    runBlocking {
      val id = 1
      val user: User = mock()
      whenever(client.fetchUser(id)).thenReturn(user)
      val result = remoteDataSource.get(id)
      assertTrue(result is Result.Success)
      assertEquals(user, (result as Result.Success).data)
    }
  }

  @Test
  fun `Fetch user by id should return error`() {
    runBlocking {
      val id = 1
      val expectedError = IOException()
      given(client.fetchUser(id)).willAnswer {
        throw expectedError
      }
      val result = remoteDataSource.get(id)
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }
}
