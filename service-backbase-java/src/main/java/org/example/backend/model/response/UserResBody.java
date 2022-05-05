package org.example.backend.model.response;

import pl.net.testit.serum.commons.json.JsonEntity;

public class UserResBody  extends JsonEntity {

  public User user;

  public static class User {

    public String email;
    public String token;
    public String username;
    public String bio;
    public String image;
  }

}
