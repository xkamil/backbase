package org.alternative.backend.endpoints.user.register

import pl.net.testit.serum.commons.json.JsonEntity

class UserRegisterRequestBody extends JsonEntity {

  User user

  static class User {
    String username
    String email
    String password
  }

}
