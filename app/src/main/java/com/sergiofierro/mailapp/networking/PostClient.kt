package com.sergiofierro.mailapp.networking

import com.sergiofierro.mailapp.networking.api.PostApi
import javax.inject.Inject

class PostClient @Inject constructor(private val postApi: PostApi) {

  suspend fun fetchPosts() = postApi.fetchPosts()
}
