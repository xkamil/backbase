package org.alternative.backend.base.config

class StaticTestData {

  static class RegisteredUsers {

    public static final User USER_1 = new User(
        '690f4aaf313',
        'b1c3344d-3050-409f-8dd1-68b4489a612d@example.com',
        '1f647f9821d4d804800a753')
  }

  static class User {

    String username
    String email
    String password

    User(String username, String email, String password) {
      this.username = username
      this.email = email
      this.password = password
    }
  }
}
