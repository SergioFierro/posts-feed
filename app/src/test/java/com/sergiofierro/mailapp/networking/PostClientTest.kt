package com.sergiofierro.mailapp.networking

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sergiofierro.mailapp.networking.api.PostAPI
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PostClientTest {

  private val api: PostAPI = mock()
  private val client = PostClient(api)

  @Test
  fun `Fetch posts should call post API`() {
    runBlocking {
      client.fetchPosts()
      verify(api).fetchPosts()
    }
  }
}
