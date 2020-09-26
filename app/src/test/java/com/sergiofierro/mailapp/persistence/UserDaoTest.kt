package com.sergiofierro.mailapp.persistence

import com.sergiofierro.mailapp.model.User
import com.sergiofierro.mailapp.persistence.dao.UserDao
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

    val postsFromDB = dao.getAll()
    assertEquals(postsFromDB[0], mockUser)
  }

  @Test
  @Throws(Exception::class)
  fun `Insert all should store posts in DB`() = runBlocking {
    val mockUser1 = User(1, "name", "email", "phone", "website")
    val mockUser2 = User(2, "name", "email", "phone", "website")
    val mockUser3 = User(3, "name", "email", "phone", "website")
    val mockList = listOf(mockUser1, mockUser2, mockUser3)
    dao.insert(mockList)

    val postsFromDB = dao.getAll()
    assertEquals(postsFromDB.toString(), mockList.toString())
    assertEquals(postsFromDB[0], mockUser1)
  }

  @Test
  @Throws(Exception::class)
  fun `Delete should delete post in DB`() = runBlocking {
    val mockUser1 = User(1, "name", "email", "phone", "website")
    val mockUser2 = User(2, "name", "email", "phone", "website")
    val mockUser3 = User(3, "name", "email", "phone", "website")
    val mockList = listOf(mockUser1, mockUser2, mockUser3)
    dao.insert(mockList)
    assertTrue(dao.getAll().isNotEmpty())

    dao.delete(mockUser1)
    assertFalse(dao.getAll().contains(mockUser1))
  }

  @Test
  @Throws(Exception::class)
  fun `Delete all should delete all posts in DB`() = runBlocking {
    val mockUser1 = User(1, "name", "email", "phone", "website")
    val mockUser2 = User(2, "name", "email", "phone", "website")
    val mockUser3 = User(3, "name", "email", "phone", "website")
    val mockList = listOf(mockUser1, mockUser2, mockUser3)
    dao.insert(mockList)
    assertTrue(dao.getAll().isNotEmpty())

    dao.deleteAll()
    assertTrue(dao.getAll().isEmpty())
  }
}
