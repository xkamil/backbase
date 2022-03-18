package org.example.backend.model.request;

public class UserRegisterReqBody {

  public User user;

  public static class User {

    public String username;
    public String email;
    public String password;
  }

}
