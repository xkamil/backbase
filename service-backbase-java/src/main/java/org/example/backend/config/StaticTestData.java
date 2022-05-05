package org.example.backend.config;

public class StaticTestData {

  public static class RegisteredUsers {

    public static final User USER_1 = new User(
        "690f4aaf313",
        "b1c3344d-3050-409f-8dd1-68b4489a612d@example.com",
        "1f647f9821d4d804800a753");
  }

  public static class User {

    public final String username;
    public final String email;
    public final String password;

    public User(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
    }
  }
}
