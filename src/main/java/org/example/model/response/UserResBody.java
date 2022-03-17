package org.example.model.response;

public class UserResBody {

  public User user;

  public static class User {

    public String email;
    public String token;
    public String username;
    public String bio;
    public String image;
  }

}
