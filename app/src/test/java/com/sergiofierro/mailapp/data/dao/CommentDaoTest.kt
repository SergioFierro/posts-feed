package com.sergiofierro.mailapp.data.dao

import com.sergiofierro.mailapp.model.Comment
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommentDaoTest : BaseDaoTest() {

  private lateinit var dao: CommentDao

  @Before
  fun setup() {
    dao = db.commentDao()
  }

  @Test
  @Throws(Exception::class)
  fun `Get by post id should return comment`() = runBlocking {
    val postId = 2
    val mockComment1 = Comment(1, 1, "name", "email", "body")
    val mockComment2 = Comment(2, 1, "name", "email", "body")
    val mockComment3 = Comment(3, postId, "name", "email", "body")
    val mockList = listOf(mockComment1, mockComment2, mockComment3)
    dao.insert(mockList)

    val commentsFromDB = dao.getByPostId(postId)
    assertEquals(mockList.filter { it.postId == postId }.toString(), commentsFromDB.toString())
  }

  @Test
  @Throws(Exception::class)
  fun `Insert should store comment in DB`() = runBlocking {
    val mockComment = Comment(1, 1, "name", "email", "body")
    dao.insert(mockComment)

    val commentsFromDB = dao.getByPostId(mockComment.postId)
    assertEquals(commentsFromDB[0], mockComment)
  }


  @Test
  @Throws(Exception::class)
  fun `Delete should delete comment in DB`() = runBlocking {
    val mockComment = Comment(1, 1, "name", "email", "body")
    dao.insert(mockComment)
    assertNotNull(dao.getByPostId(mockComment.postId))

    dao.delete(mockComment)
    assertFalse(dao.getByPostId(mockComment.postId).contains(mockComment))
  }
}
