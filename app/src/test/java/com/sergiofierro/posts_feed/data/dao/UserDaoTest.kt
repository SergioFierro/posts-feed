package com.sergiofierro.posts_feed.data.dao

import com.sergiofierro.posts_feed.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserDaoTest : BaseDaoTest() {

  private lateinit var dao: UserDao

  @Before
  fun setup() {
    dao = db.userDao()
  }

  @Test
  @Throws(Exception::class)
  fun `Get by user id should return user`() = runBlocking {
    val mockUser1 = User(1, "name", "email", "phone", "website")
    val mockUser2 = User(2, "name", "email", "phone", "website")
    val mockUser3 = User(3, "name", "email", "phone", "website")
    val mockList = listOf(mockUser1, mockUser2, mockUser3)
    dao.insert(mockList)

    val userFromDB = dao.get(mockUser2.id)
    assertEquals(mockUser2.toString(), userFromDB.toString())
  }

  @Test
  @Throws(Exception::class)
  fun `Insert should store post in DB`() = runBlocking {
    val mockUser = User(1, "name", "email", "phone", "website")
    dao.insert(mockUser)

    val postsFromDB = dao.get(mockUser.id)
    assertEquals(postsFromDB, mockUser)
  }

  @Test
  @Throws(Exception::class)
  fun `Insert all should store posts in DB`() = runBlocking {
    val mockUser1 = User(1, "name", "email", "phone", "website")
    val mockUser2 = User(2, "name", "email", "phone", "website")
    val mockUser3 = User(3, "name", "email", "phone", "website")
    val mockList = listOf(mockUser1, mockUser2, mockUser3)
    dao.insert(mockList)

    val postsFromDB = dao.get(mockUser2.id)
    assertEquals(postsFromDB.toString(), mockUser2.toString())
  }

  @Test
  @Throws(Exception::class)
  fun `Delete should delete post in DB`() = runBlocking {
    val mockUser = User(1, "name", "email", "phone", "website")

    dao.insert(mockUser)
    assertNotNull(dao.get(mockUser.id))

    dao.delete(mockUser)
    assertNull(dao.get(mockUser.id))
  }
}
