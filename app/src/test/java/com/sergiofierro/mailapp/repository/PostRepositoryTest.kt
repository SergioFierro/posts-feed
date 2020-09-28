package com.sergiofierro.mailapp.repository

import com.nhaarman.mockitokotlin2.*
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.local.PostLocalDataSource
import com.sergiofierro.mailapp.data.remote.PostRemoteDataSource
import com.sergiofierro.mailapp.model.Post
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException

class PostRepositoryTest {

  private val remoteDataSource: PostRemoteDataSource = mock()
  private val localDataSource: PostLocalDataSource = mock()
  private val repository = PostRepository(remoteDataSource, localDataSource)

  @Test
  fun `Fetch posts should call api and return expected items`() {
    runBlocking {
      val posts = listOf<Post>(mock())
      whenever(remoteDataSource.fetchPosts()).thenReturn(Result.Success(posts))
      whenever(localDataSource.getAll()).thenReturn(Result.Success(posts))
      val result = repository.fetchAll()
      verify(localDataSource).save(posts)
      assertTrue(result is Result.Success)
      assertEquals(posts, (result as Result.Success).data)
    }
  }

  @Test
  fun `Fetch posts should return error`() {
    runBlocking {
      val expectedError = IOException()
      given(remoteDataSource.fetchPosts()).willAnswer {
        throw expectedError
      }
      val result = repository.fetchAll()
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }

  @Test
  fun `Delete post should call local data source`() {
    runBlocking {
      val post: Post = mock()
      repository.delete(post)
      verify(localDataSource).delete(post)
    }
  }

  @Test
  fun `Delete posts should call local data source`() {
    runBlocking {
      repository.deleteAll()
      verify(localDataSource).deleteAll()
    }
  }

  @Test
  fun `Set favorite to true should modify favorite boolean and save to local data source`() {
    runBlocking {
      val post = Post(1, 1, "title", "body", favorite = false, unread = true)
      val capturedPost = argumentCaptor<Post>().apply {
        whenever(localDataSource.save(capture())).thenReturn(Unit)
      }
      repository.changeFavorite(post)
      assertTrue(capturedPost.firstValue.favorite)
    }
  }

  @Test
  fun `Set favorite to false should modify favorite boolean and save to local data source`() {
    runBlocking {
      val post = Post(1, 1, "title", "body", favorite = true, unread = true)
      val capturedPost = argumentCaptor<Post>().apply {
        whenever(localDataSource.save(capture())).thenReturn(Unit)
      }
      repository.changeFavorite(post)
      assertFalse(capturedPost.firstValue.favorite)
    }
  }

  @Test
  fun `Post read should modify unread boolean and save to local data source`() {
    runBlocking {
      val post = Post(1, 1, "title", "body", favorite = true, unread = true)
      val capturedPost = argumentCaptor<Post>().apply {
        whenever(localDataSource.save(capture())).thenReturn(Unit)
      }
      repository.postRead(post)
      assertFalse(capturedPost.firstValue.unread)
    }
  }
}
