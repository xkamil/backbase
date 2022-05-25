package org.alternative.backend.endpoints.user.register


import static org.example.backend.extensions.DataGenerator.*

class UserRegisterRequestSampler  {

  UserRegisterRequestBody minimal() {
    new UserRegisterRequestBody().tap {
      user = new UserRegisterRequestBody.User().tap {
        username = generateUsername()
        email = generateEmail()
        password = generatePassword()
      }
    }
  }

  UserRegisterRequestBody full() { minimal() }

}
