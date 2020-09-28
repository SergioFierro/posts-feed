package com.sergiofierro.posts_feed.networking

import com.sergiofierro.posts_feed.networking.api.PostApi
import javax.inject.Inject

class PostClient @Inject constructor(private val postApi: PostApi) {

  suspend fun fetchPosts() = postApi.fetchPosts()
}
