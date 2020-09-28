package com.sergiofierro.posts_feed.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergiofierro.posts_feed.MainCoroutineRule
import com.sergiofierro.posts_feed.model.Post
import com.sergiofierro.posts_feed.repository.mock.PostRepositoryMock
import com.sergiofierro.posts_feed.util.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostViewModelTest {

  private val postRepository = PostRepositoryMock()
  private lateinit var postViewModel: PostViewModel

  private val postList = listOf(
    Post(1, 123, "title", "body", favorite = true, unread = false),
    Post(2, 123, "title", "body", favorite = false, unread = false),
    Post(3, 123, "title", "body", favorite = false, unread = false)
  )

  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    postRepository.addPosts(postList)
    postViewModel = PostViewModel(postRepository)
  }

  @Test
  fun `Fetch with list type = favorite post should only return favorite posts`() {
    postViewModel.setListType(ListType.FAVORITES)
    postViewModel.posts.observeForTesting {
      assertEquals(postList.filter { it.favorite }.size, postViewModel.posts.value!!.size)
    }
  }

  @Test
  fun `Fetch with list type = all post should return all posts`() {
    postViewModel.setListType(ListType.ALL)
    postViewModel.posts.observeForTesting {
      assertEquals(postList.size, postViewModel.posts.value!!.size)
    }
  }

  @Test
  fun `Delete post`() {
    postViewModel.deletePost(postList.first())
    postViewModel.posts.observeForTesting {
      assertEquals(postList.size - 1, postViewModel.posts.value!!.size)
    }
  }
}
