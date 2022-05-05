package org.example.backend.sampler;

import org.example.backend.model.request.UserLoginReqBody;
import org.example.backend.model.request.UserLoginReqBody.User;

public class UserLoginSampler {

  public static UserLoginReqBody fullInput(String email, String password) {
    var user = new User()
        .email(email)
        .password(password);
    return new UserLoginReqBody().user(user);
  }

}
