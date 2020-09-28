package com.sergiofierro.mailapp.ui.main

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.sergiofierro.mailapp.repository.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MainViewModelTest {

  private val postRepository: PostRepository = mock()
  private val mainViewModel = MainViewModel(postRepository)

  @Test
  fun `Refresh should fetch posts`() {
    runBlocking {
      mainViewModel.refresh()
      verify(postRepository).fetchAll()
    }
  }

  @Test
  fun `Delete all should delete posts`() {
    runBlocking {
      mainViewModel.deleteAll()
      verify(postRepository).deleteAll()
    }
  }
}
