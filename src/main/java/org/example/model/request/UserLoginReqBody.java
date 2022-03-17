package org.example.model.request;

public class UserLoginReqBody {

  public User user;

  public static class User {

    public String email;
    public String password;
  }

}
