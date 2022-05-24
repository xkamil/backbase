package org.alternative.backend.endpoints.user

import pl.net.testit.serum.commons.json.JsonEntity

class UserResponseBody extends JsonEntity {

  User user

  static class User {
    String email
    String token
    String username
    String bio
    String image
  }

}
