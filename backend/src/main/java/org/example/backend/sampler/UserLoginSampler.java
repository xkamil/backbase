package org.example.backend.sampler;

import org.example.backend.api.user.UserLoginReqBody;
import org.example.backend.api.user.UserLoginReqBody.User;

public class UserLoginSampler {

  public static UserLoginReqBody fullInput(String email, String password) {
    var reqBody = new UserLoginReqBody();
    reqBody.user = new User();
    reqBody.user.email = email;
    reqBody.user.password = password;
    return reqBody;
  }

}
