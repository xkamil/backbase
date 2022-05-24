package org.alternative.backend.endpoints.user.register

import org.alternative.backend.base.RequestSampler

import static org.example.backend.extensions.DataGenerator.*

class UserRegisterRequestSampler implements RequestSampler<UserRegisterRequestBody> {

  @Override
  UserRegisterRequestBody minimal() {
    new UserRegisterRequestBody().tap {
      user = new UserRegisterRequestBody.User().tap {
        username = generateUsername()
        email = generateEmail()
        password = generatePassword()
      }
    }
  }

  @Override
  UserRegisterRequestBody full() { minimal() }

}
