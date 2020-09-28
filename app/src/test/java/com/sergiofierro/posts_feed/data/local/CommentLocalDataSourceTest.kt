package com.sergiofierro.posts_feed.data.local

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sergiofierro.posts_feed.data.Result
import com.sergiofierro.posts_feed.data.dao.CommentDao
import com.sergiofierro.posts_feed.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CommentLocalDataSourceTest {

  private val commentDao: CommentDao = mock()
  private val localDataSource = CommentLocalDataSource(commentDao, Dispatchers.Unconfined)

  @Test
  fun `Get by post id should return expected comments`() {
    runBlocking {
      val id = 1
      val comments = listOf<Comment>(mock())
      whenever(commentDao.getByPostId(id)).thenReturn(comments)
      val result = localDataSource.getByPostId(id)
      assertTrue(result is Result.Success)
      assertEquals(comments, (result as Result.Success).data)
    }
  }

  @Test
  fun `Get comments by post id should return error`() {
    runBlocking {
      val id = 1
      val expectedError = IOException()
      given(commentDao.getByPostId(id)).willAnswer {
        throw expectedError
      }
      val result = localDataSource.getByPostId(id)
      assertTrue(result is Result.Error)
      assertEquals(expectedError, (result as Result.Error).exception)
    }
  }

  @Test
  fun `Save comment should call dao`() {
    runBlocking {
      val comments = listOf<Comment>(mock())
      localDataSource.save(comments)
      verify(commentDao).insert(comments)
    }
  }
}
