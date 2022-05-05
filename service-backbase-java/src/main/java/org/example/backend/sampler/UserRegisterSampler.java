package org.example.backend.sampler;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.val;
import org.example.backend.model.request.UserRegisterReqBody;
import org.example.backend.model.request.UserRegisterReqBody.User;

public class UserRegisterSampler {

  @SafeVarargs
  public static UserRegisterReqBody fullInput(Consumer<User>... modify) {
    val user = new User()
        .username(UUID.randomUUID().toString().substring(25))
        .email(UUID.randomUUID() + "@example.com")
        .password(UUID.randomUUID().toString().substring(10).replace("-", ""));

    Arrays.stream(modify).forEach(m -> m.accept(user));
    return new UserRegisterReqBody().user(user);
  }

}
