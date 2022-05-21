package org.example.backend.model.response

class UserResBody(
  val user: UserResBodyUser? = null,
)

class UserResBodyUser(
  val email: String? = null,
  val token: String? = null,
  val username: String? = null,
  val bio: String? = null,
  val image: String? = null,
)