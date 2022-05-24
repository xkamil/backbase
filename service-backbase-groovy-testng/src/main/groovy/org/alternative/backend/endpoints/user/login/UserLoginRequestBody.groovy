package org.alternative.backend.endpoints.user.login

import pl.net.testit.serum.commons.json.JsonEntity

class UserLoginRequestBody extends JsonEntity {

  User user

  static class User {
    String email
    String password
  }

}
