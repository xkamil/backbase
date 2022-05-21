package org.example.backend.sampler

import org.example.backend.model.request.UserLoginReqBody
import org.example.backend.model.request.UserLoginReqBodyUser

object UserLoginReqBodySampler {

  fun fullInput(email: String?, password: String?): UserLoginReqBody = UserLoginReqBody(
    user = UserLoginReqBodyUser(
      email,
      password
    )
  )
}