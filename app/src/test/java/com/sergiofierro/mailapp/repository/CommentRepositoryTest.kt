package com.sergiofierro.mailapp.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.*
import com.sergiofierro.mailapp.model.Comment
import com.sergiofierro.mailapp.networking.CommentClient
import com.sergiofierro.mailapp.persistence.dao.CommentDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class CommentRepositoryTest {

  private val commentClient: CommentClient = mock()
  private val commentDao: CommentDao = mock()
  private val commentRepository = CommentRepository(commentClient, commentDao)

  @Test
  fun `Fetch comments with no comments stored should call api and return expected items`() {
    runBlocking {
      val postId = 123
      val expectedResponse = listOf<Comment>(mock())
      whenever(commentDao.getByPostId(postId)).thenReturn(emptyList())
      whenever(commentClient.fetchComments(postId)).thenReturn(expectedResponse)
      commentRepository.fetchComments(postId).test {
        assertEquals(expectedResponse.size, expectItem().size)
        expectComplete()
      }
      verify(commentDao).getByPostId(postId)
      verify(commentClient).fetchComments(postId)
    }
  }

  @Test
  fun `Fetch comments from database should return expected items`() {
    runBlocking {
      val postId = 123
      val expectedResponse = listOf<Comment>(mock())
      whenever(commentDao.getByPostId(postId)).thenReturn(expectedResponse)
      commentRepository.fetchComments(postId).test {
        assertEquals(expectedResponse.size, expectItem().size)
        expectComplete()
      }
      verify(commentDao).getByPostId(postId)
      verify(commentClient, never()).fetchComments(any())
    }
  }

  @InternalCoroutinesApi
  @Test
  fun `Fetch comments with exception should fail`() {
    runBlocking {
      val expectedError = IOException()
      given(commentClient.fetchComments(any())).willAnswer {
        throw expectedError
      }
      commentRepository.fetchComments(123).test {
        assertEquals(expectedError::class.java, expectError()::class.java)
      }
      verify(commentClient).fetchComments(any())
    }
  }
}
