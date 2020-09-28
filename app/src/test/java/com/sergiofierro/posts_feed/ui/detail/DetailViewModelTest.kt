package com.sergiofierro.posts_feed.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergiofierro.posts_feed.MainCoroutineRule
import com.sergiofierro.posts_feed.model.Comment
import com.sergiofierro.posts_feed.model.Post
import com.sergiofierro.posts_feed.model.User
import com.sergiofierro.posts_feed.repository.mock.CommentRepositoryMock
import com.sergiofierro.posts_feed.repository.mock.PostRepositoryMock
import com.sergiofierro.posts_feed.repository.mock.UserRepositoryMock
import com.sergiofierro.posts_feed.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

  private val postRepository = PostRepositoryMock()
  private val userRepository = UserRepositoryMock()
  private val commentRepository = CommentRepositoryMock()
  private lateinit var detailViewModel: DetailViewModel

  private val postList = listOf(
    Post(1, 1, "title", "body", favorite = true, unread = false),
    Post(2, 1, "title", "body", favorite = false, unread = true),
    Post(3, 2, "title", "body", favorite = false, unread = false)
  )

  private val userList = listOf(
    User(1, "name", "email", "phone", "website"),
    User(2, "name", "email", "phone", "website"),
    User(3, "name", "email", "phone", "website")
  )

  private val commentList = listOf(
    Comment(1, 1, "name", "email", "body"),
    Comment(2, 1, "name", "email", "body"),
    Comment(3, 1, "name", "email", "body"),
    Comment(4, 2, "name", "email", "body"),
    Comment(5, 3, "name", "email", "body")
  )

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    postRepository.addPosts(postList)
    userRepository.addUsers(userList)
    commentRepository.addComments(commentList)
    detailViewModel = DetailViewModel(postRepository, userRepository, commentRepository)
  }

  @Test
  fun `Fetch user by id should return only user related to that id`() {
    val post = postList.first()
    detailViewModel.setPost(post)
    detailViewModel.user.observeForTesting {
      assertEquals(userList.find { it.id == post.userId }, detailViewModel.user.value)
    }
  }

  @Test
  fun `Fetch comments by post id should return only comments related to that id`() {
    detailViewModel.setPost(postList.first())
    detailViewModel.comments.observeForTesting {
      assertEquals(commentList.filter { it.postId == postList.first().id }.size, detailViewModel.comments.value!!.size)
    }
  }

  @Test
  fun `Set post that is not favorite, observable should return false`() {
    detailViewModel.setPost(postList.first { !it.favorite })
    assertFalse(detailViewModel.isFavorite.get())
  }

  @Test
  fun `Set post that is favorite, observable should return true`() {
    detailViewModel.setPost(postList.first { it.favorite })
    assertTrue(detailViewModel.isFavorite.get())
  }

  @Test
  fun `Set post that is unread should set it to false`() {
    detailViewModel.setPost(postList.first { it.unread })
    detailViewModel._post.observeForTesting {
      assertFalse(detailViewModel._post.value!!.unread)
    }
  }

  @Test
  fun `Set post as not favorite should set it to false`() {
    detailViewModel.setPost(postList.first { it.favorite })
    detailViewModel.changeFavorite()
    detailViewModel._post.observeForTesting {
      assertFalse(detailViewModel._post.value!!.favorite)
    }
  }
  @Test
  fun `Set post as favorite should set it to true`() {
    detailViewModel.setPost(postList.first { !it.favorite })
    detailViewModel.changeFavorite()
    detailViewModel._post.observeForTesting {
      assertTrue(detailViewModel._post.value!!.favorite)
    }
  }
}
