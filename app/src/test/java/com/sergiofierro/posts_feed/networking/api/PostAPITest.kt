package com.sergiofierro.posts_feed.networking.api

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PostAPITest : BaseApiTest<PostApi>() {

  private lateinit var service: PostApi

  @Before
  fun setup() {
    service = createService(PostApi::class.java)
  }

  @Test
  fun `Fetch posts with valid response should succeed`() = runBlocking {
    enqueueMockResponse("posts_response.json")
    val response = service.fetchPosts()
    mockWebServer.takeRequest()

    assertTrue(response.isNotEmpty())
  }

  @Test(expected = JsonDataException::class)
  fun `Fetch posts with invalid response should fail`() {
    runBlocking {
      enqueueMockResponse("invalid_response.json")
      service.fetchPosts()
    }
  }
}
