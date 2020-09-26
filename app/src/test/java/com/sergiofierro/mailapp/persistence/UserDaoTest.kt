package com.sergiofierro.mailapp.persistence

import com.sergiofierro.mailapp.model.User
import com.sergiofierro.mailapp.persistence.dao.UserDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
  fun `Insert should store post in DB`() = runBlocking {
    val mockUser = User(1, "name", "email", "phone", "website")
    dao.insert(mockUser)

    val postsFromDB = dao.getAll()
    Assert.assertEquals(postsFromDB[0], mockUser)
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
    Assert.assertEquals(postsFromDB.toString(), mockList.toString())
    Assert.assertEquals(postsFromDB[0], mockUser1)
  }

  @Test
  @Throws(Exception::class)
  fun `Delete should delete post in DB`() = runBlocking {
    val mockUser1 = User(1, "name", "email", "phone", "website")
    val mockUser2 = User(2, "name", "email", "phone", "website")
    val mockUser3 = User(3, "name", "email", "phone", "website")
    val mockList = listOf(mockUser1, mockUser2, mockUser3)
    dao.insert(mockList)
    Assert.assertTrue(dao.getAll().isNotEmpty())

    dao.delete(mockUser1)
    Assert.assertFalse(dao.getAll().contains(mockUser1))
  }

  @Test
  @Throws(Exception::class)
  fun `Delete all should delete all posts in DB`() = runBlocking {
    val mockUser1 = User(1, "name", "email", "phone", "website")
    val mockUser2 = User(2, "name", "email", "phone", "website")
    val mockUser3 = User(3, "name", "email", "phone", "website")
    val mockList = listOf(mockUser1, mockUser2, mockUser3)
    dao.insert(mockList)
    Assert.assertTrue(dao.getAll().isNotEmpty())

    dao.deleteAll()
    Assert.assertTrue(dao.getAll().isEmpty())
  }
}
