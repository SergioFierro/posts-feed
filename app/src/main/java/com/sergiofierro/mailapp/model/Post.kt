package com.sergiofierro.mailapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
  @field:Json(name = "id") val id: Int,
  @field:Json(name = "userId") val userId: Int,
  @field:Json(name = "title") val title: String,
  @field:Json(name = "body") val body: String
)
