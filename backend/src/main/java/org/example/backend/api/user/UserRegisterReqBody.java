package org.example.backend.api.user;

public class UserRegisterReqBody {

  public User user;

  public static class User {

    public String username;
    public String email;
    public String password;
  }

}
