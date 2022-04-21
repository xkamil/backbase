package org.example.backend.sampler;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.val;
import org.example.backend.api.user.UserRegisterReqBody;
import org.example.backend.api.user.UserRegisterReqBody.User;

public class UserRegisterSampler {

  @SafeVarargs
  public static UserRegisterReqBody fullInput(Consumer<UserRegisterReqBody>... modify) {
    var reqBody = new UserRegisterReqBody();
    val user = new User();
    reqBody.user = new User();
    reqBody.user.username = UUID.randomUUID().toString().substring(25);
    reqBody.user.email = UUID.randomUUID() + "@example.com";
    reqBody.user.password = UUID.randomUUID().toString().substring(10).replace("-", "");

    Arrays.stream(modify).forEach(m -> m.accept(reqBody));
    return reqBody;
  }

}
