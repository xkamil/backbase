package org.example.backend.model.request

class UserRegisterReqBody(
  var user: UserRegisterReqBodyUser?
)

class UserRegisterReqBodyUser(
  var username: String?,
  var email: String?,
  var password: String?
)