package com.sergiofierro.posts_feed.networking

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sergiofierro.posts_feed.networking.api.UserApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UserClientTest {

  private val api: UserApi = mock()
  private val client = UserClient(api)

  @Test
  fun `Fetch user should call user API`() {
    runBlocking {
      val id = 1
      client.fetchUser(id)
      verify(api).fetchUser(id)
    }
  }
}
