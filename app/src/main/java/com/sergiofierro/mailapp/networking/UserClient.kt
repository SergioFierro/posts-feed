package com.sergiofierro.mailapp.networking

import com.sergiofierro.mailapp.networking.api.UserApi
import javax.inject.Inject

class UserClient @Inject constructor(private val userApi: UserApi) {
  suspend fun fetchUser(id: Int) = userApi.fetchUser(id)
}
