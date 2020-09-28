package com.sergiofierro.posts_feed.networking

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sergiofierro.posts_feed.networking.api.CommentApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CommentsClientTest {

  private val api: CommentApi = mock()
  private val client = CommentClient(api)

  @Test
  fun `Fetch comments should call comments API`() {
    runBlocking {
      val id = 1
      client.fetchComments(id)
      verify(api).fetchComments(id)
    }
  }
}
