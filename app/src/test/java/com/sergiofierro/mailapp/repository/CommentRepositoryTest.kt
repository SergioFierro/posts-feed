package com.sergiofierro.mailapp.repository

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.mailapp.data.Result
import com.sergiofierro.mailapp.data.local.CommentLocalDataSource
import com.sergiofierro.mailapp.data.remote.CommentRemoteDataSource
import com.sergiofierro.mailapp.model.Comment
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class CommentRepositoryTest {

  private val remoteDataSource: CommentRemoteDataSource = mock()
  private val localDataSource: CommentLocalDataSource = mock()
  private val repository = CommentRepository(remoteDataSource, localDataSource)

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
