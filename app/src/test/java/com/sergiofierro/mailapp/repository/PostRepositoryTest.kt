package com.sergiofierro.mailapp.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.*
import com.sergiofierro.mailapp.model.Post
import com.sergiofierro.mailapp.networking.PostClient
import com.sergiofierro.mailapp.persistence.dao.PostDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class PostRepositoryTest {

  private val postClient: PostClient = mock()
  private val postDao: PostDao = mock()
  private val postRepository = PostRepository(postClient, postDao)

  @Test
  fun `Fetch posts with no posts stored should call api and return expected items`() {
    runBlocking {
      val expectedResponse = listOf<Post>(mock())
      whenever(postDao.getAll()).thenReturn(emptyList())
      whenever(postClient.fetchPosts()).thenReturn(expectedResponse)
      postRepository.fetchPosts().test {
        assertEquals(expectedResponse.size, expectItem().size)
        expectComplete()
      }
      verify(postDao).getAll()
      verify(postClient).fetchPosts()
    }
  }

  @Test
  fun `Fetch posts from database should return expected items`() {
    runBlocking {
      val expectedResponse = listOf<Post>(mock())
      whenever(postDao.getAll()).thenReturn(expectedResponse)
      postRepository.fetchPosts().test {
        assertEquals(expectedResponse.size, expectItem().size)
        expectComplete()
      }
      verify(postDao).getAll()
      verify(postClient, never()).fetchPosts()
    }
  }

  @InternalCoroutinesApi
  @Test
  fun `Fetch posts with exception should fail`() {
    runBlocking {
      val expectedError = IOException()
      given(postClient.fetchPosts()).willAnswer {
        throw expectedError
      }
      postRepository.fetchPosts().test {
        assertEquals(expectedError::class.java, expectError()::class.java)
      }
      verify(postClient).fetchPosts()
    }
  }
}
