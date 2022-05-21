package org.example.backend.config

class StaticTestData {
  object RegisteredUsers {
    val USER_1 = User(
      "690f4aaf313",
      "b1c3344d-3050-409f-8dd1-68b4489a612d@example.com",
      "1f647f9821d4d804800a753")
  }

  class User(val username: String, val email: String, val password: String)
}