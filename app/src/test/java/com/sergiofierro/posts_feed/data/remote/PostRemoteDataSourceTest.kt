package com.sergiofierro.posts_feed.data.remote

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.model.Post
import com.sergiofierro.posts_feed.networking.PostClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class PostRemoteDataSourceTest {

  private val client: PostClient = mock()
  private val remoteDataSource = PostRemoteDataSource(client, Dispatchers.Unconfined)

  @Test
  fun `Fetch posts should return expected comments`() {
    runBlocking {
      val posts = listOf<Post>(mock())
      whenever(client.fetchPosts()).thenReturn(posts)
      val result = remoteDataSource.fetchPosts()
      assertTrue(result is Result.Success)
      assertEquals(posts, (result as Result.Success).data)
    }
  }

  @Test
  fun `Fetch posts should return error`() {
    runBlocking {
      val expectedError = IOException()
      given(client.fetchPosts()).willAnswer {
        throw expectedError
      }
      val result = remoteDataSource.fetchPosts()
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }
}
