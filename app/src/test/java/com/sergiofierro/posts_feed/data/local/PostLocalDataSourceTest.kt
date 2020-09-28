package com.sergiofierro.posts_feed.data.local

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.dao.PostDao
import com.sergiofierro.posts_feed.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class PostLocalDataSourceTest {

  private val postDao: PostDao = mock()
  private val localDataSource = PostLocalDataSource(postDao, Dispatchers.Unconfined)

  @Test
  fun `Get all posts should return expected posts`() {
    runBlocking {
      val posts = listOf<Post>(mock())
      whenever(postDao.getAll()).thenReturn(posts)
      val result = localDataSource.getAll()
      assertTrue(result is Result.Success)
      assertEquals(posts, (result as Result.Success).data)
    }
  }

  @Test
  fun `Get all posts should return error`() {
    runBlocking {
      val expectedError = IOException()
      given(postDao.getAll()).willAnswer {
        throw expectedError
      }
      val result = localDataSource.getAll()
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }

  @Test
  fun `Save post should call dao`() {
    runBlocking {
      val post: Post = mock()
      localDataSource.save(post)
      verify(postDao).insert(post)
    }
  }

  @Test
  fun `Save posts should call dao`() {
    runBlocking {
      val posts = listOf<Post>(mock())
      localDataSource.save(posts)
      verify(postDao).insert(posts)
    }
  }

  @Test
  fun `Delete post should call dao`() {
    runBlocking {
      val post: Post = mock()
      localDataSource.delete(post)
      verify(postDao).delete(post)
    }
  }

  @Test
  fun `Delete all posts should call dao`() {
    runBlocking {
      localDataSource.deleteAll()
      verify(postDao).deleteAll()
    }
  }
}
