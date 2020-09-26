package com.sergiofierro.mailapp.networking

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sergiofierro.mailapp.networking.api.CommentsApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CommentsClientTest {

  private val api: CommentsApi = mock()
  private val client = CommentsClient(api)

  @Test
  fun `Fetch comments should call comments API`() {
    runBlocking {
      val id = 1
      client.fetchComments(id)
      verify(api).fetchComments(id)
    }
  }
}
