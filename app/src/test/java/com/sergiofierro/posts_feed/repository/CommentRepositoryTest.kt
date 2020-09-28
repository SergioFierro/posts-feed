package com.sergiofierro.posts_feed.repository

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.local.CommentLocalDataSource
import com.sergiofierro.posts_feed.data.remote.CommentRemoteDataSource
import com.sergiofierro.posts_feed.model.Comment
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class CommentRepositoryTest {

  private val remoteDataSource: CommentRemoteDataSource = mock()
  private val localDataSource: CommentLocalDataSource = mock()
  private val repository = DefaultCommentRepository(remoteDataSource, localDataSource)

  @Test
  fun `Fetch comments should call api and return expected items`() {
    runBlocking {
      val postId = 123
      val comments = listOf<Comment>(mock())
      whenever(remoteDataSource.fetchComments(postId)).thenReturn(Result.Success(comments))
      whenever(localDataSource.getByPostId(postId)).thenReturn(Result.Success(comments))
      val result = repository.fetchComments(postId)
      verify(localDataSource).save(comments)
      assertTrue(result is Result.Success)
      assertEquals(comments, (result as Result.Success).data)
    }
  }

  @Test
  fun `Fetch comments should return error`() {
    runBlocking {
      val postId = 123
      val expectedError = IOException()
      given(remoteDataSource.fetchComments(postId)).willAnswer {
        throw expectedError
      }
      val result = repository.fetchComments(postId)
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }
}
