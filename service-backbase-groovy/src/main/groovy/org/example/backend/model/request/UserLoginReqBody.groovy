package org.example.backend.model.request

import pl.net.testit.serum.commons.json.JsonEntity

class UserLoginReqBody extends JsonEntity {

  User user

  static class User {
    String email
    String password
  }

}
