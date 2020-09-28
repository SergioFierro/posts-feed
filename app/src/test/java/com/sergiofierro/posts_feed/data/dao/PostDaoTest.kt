package com.sergiofierro.posts_feed.data.dao

import com.sergiofierro.posts_feed.model.Post
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PostDaoTest : BaseDaoTest() {

  private lateinit var dao: PostDao

  @Before
  fun setup() {
    dao = db.postDao()
  }

  @Test
  @Throws(Exception::class)
  fun `Insert should store post in DB`() = runBlocking {
    val mockPost = Post(1, 123, "Post title", "Post body")
    dao.insert(mockPost)

    val postsFromDB = dao.getAll()
    assertEquals(postsFromDB[0], mockPost)
  }

  @Test
  @Throws(Exception::class)
  fun `Insert all should store posts in DB`() = runBlocking {
    val mockPost1 = Post(1, 123, "Post title", "Post body")
    val mockPost2 = Post(2, 123, "Post title", "Post body")
    val mockPost3 = Post(3, 123, "Post title", "Post body")
    val mockList = listOf(mockPost1, mockPost2, mockPost3)
    dao.insert(mockList)

    val postsFromDB = dao.getAll()
    assertEquals(postsFromDB.toString(), mockList.toString())
    assertEquals(postsFromDB[0], mockPost1)
  }

  @Test
  @Throws(Exception::class)
  fun `Delete should delete post in DB`() = runBlocking {
    val mockPost1 = Post(1, 123, "Post title", "Post body")
    val mockPost2 = Post(2, 123, "Post title", "Post body")
    val mockPost3 = Post(3, 123, "Post title", "Post body")
    val mockList = listOf(mockPost1, mockPost2, mockPost3)
    dao.insert(mockList)
    assertTrue(dao.getAll().isNotEmpty())

    dao.delete(mockPost1)
    assertFalse(dao.getAll().contains(mockPost1))
  }

  @Test
  @Throws(Exception::class)
  fun `Delete all should delete all posts in DB`() = runBlocking {
    val mockPost1 = Post(1, 123, "Post title", "Post body")
    val mockPost2 = Post(2, 123, "Post title", "Post body")
    val mockPost3 = Post(3, 123, "Post title", "Post body")
    val mockList = listOf(mockPost1, mockPost2, mockPost3)
    dao.insert(mockList)
    assertTrue(dao.getAll().isNotEmpty())

    dao.deleteAll()
    assertTrue(dao.getAll().isEmpty())
  }
}
