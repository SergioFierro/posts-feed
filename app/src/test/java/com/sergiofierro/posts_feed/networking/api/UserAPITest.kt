package com.sergiofierro.posts_feed.networking.api

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserAPITest : BaseApiTest<UserApi>() {

  private lateinit var service: UserApi

  @Before
  fun setup() {
    service = createService(UserApi::class.java)
  }

  @Test
  fun `Fetch user with valid response should succeed`() = runBlocking {
    enqueueMockResponse("user_response.json")
    val response = service.fetchUser(1)
    mockWebServer.takeRequest()

    assertTrue(response.id == 1)
  }

  @Test(expected = JsonDataException::class)
  fun `Fetch user with invalid response should fail`() {
    runBlocking {
      enqueueMockResponse("invalid_response.json")
      service.fetchUser(1)
    }
  }
}
