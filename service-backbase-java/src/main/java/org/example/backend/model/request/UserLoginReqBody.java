package org.example.backend.model.request;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.net.testit.serum.commons.json.JsonEntity;

@Setter
@Accessors(fluent = true)
public class UserLoginReqBody extends JsonEntity {

  public User user;

  @Setter
  @Accessors(fluent = true)
  public static class User {

    public String email;
    public String password;
  }

}
