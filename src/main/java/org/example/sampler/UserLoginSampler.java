package org.example.sampler;

import org.example.model.request.UserLoginReqBody;
import org.example.model.request.UserLoginReqBody.User;

public class UserLoginSampler {

  public static UserLoginReqBody fullInput(String email, String password) {
    var reqBody = new UserLoginReqBody();
    reqBody.user = new User();
    reqBody.user.email = email;
    reqBody.user.password = password;
    return reqBody;
  }

}
