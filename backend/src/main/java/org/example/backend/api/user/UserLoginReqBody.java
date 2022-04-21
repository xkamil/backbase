package org.example.backend.api.user;

public class UserLoginReqBody {

  public User user;

  public static class User {

    public String email;
    public String password;
  }

}
