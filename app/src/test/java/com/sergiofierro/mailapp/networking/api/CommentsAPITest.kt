package com.sergiofierro.mailapp.networking.api

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CommentsAPITest : BaseApiTest<CommentApi>() {

  private lateinit var service: CommentApi

  @Before
  fun setup() {
    service = createService(CommentApi::class.java)
  }

  @Test
  fun `Fetch comments with valid response should succeed`() = runBlocking {
    enqueueMockResponse("comments_response.json")
    val response = service.fetchComments(1)
    mockWebServer.takeRequest()

    assertTrue(response.isNotEmpty())
  }

  @Test(expected = JsonDataException::class)
  fun `Fetch comments with invalid response should fail`() {
    runBlocking {
      enqueueMockResponse("invalid_response.json")
      service.fetchComments(1)
    }
  }
}
