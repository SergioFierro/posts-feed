package com.sergiofierro.mailapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Comment(
  @field:Json(name = "id") @PrimaryKey val id: Int,
  @field:Json(name = "postId") val postId: Int,
  @field:Json(name = "name") val name: String,
  @field:Json(name = "email") val email: String,
  @field:Json(name = "body") val body: String
)
