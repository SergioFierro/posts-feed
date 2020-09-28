package com.sergiofierro.posts_feed.data.remote

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.model.Comment
import com.sergiofierro.posts_feed.networking.CommentClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class CommentRemoteDataSourceTest {

  private val client: CommentClient = mock()
  private val remoteDataSource = CommentRemoteDataSource(client, Dispatchers.Unconfined)

  @Test
  fun `Fetch comments by post id should return expected comments`() {
    runBlocking {
      val postId = 1
      val comments = listOf<Comment>(mock())
      whenever(client.fetchComments(postId)).thenReturn(comments)
      val result = remoteDataSource.fetchComments(postId)
      assertTrue(result is Result.Success)
      assertEquals(comments, (result as Result.Success).data)
    }
  }

  @Test
  fun `Fetch comments by post id should return error`() {
    runBlocking {
      val postId = 1
      val expectedError = IOException()
      given(client.fetchComments(postId)).willAnswer {
        throw expectedError
      }
      val result = remoteDataSource.fetchComments(postId)
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }
}
