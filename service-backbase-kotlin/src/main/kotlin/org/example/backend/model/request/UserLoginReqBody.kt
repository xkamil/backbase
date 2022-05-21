package org.example.backend.model.request

class UserLoginReqBody(
        var user: UserLoginReqBodyUser?
)

class UserLoginReqBodyUser(
        var email: String?,
        var password: String?
)