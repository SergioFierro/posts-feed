package com.sergiofierro.posts_feed.networking

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sergiofierro.posts_feed.networking.api.PostApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PostClientTest {

  private val api: PostApi = mock()
  private val client = PostClient(api)

  @Test
  fun `Fetch posts should call post API`() {
    runBlocking {
      client.fetchPosts()
      verify(api).fetchPosts()
    }
  }
}
