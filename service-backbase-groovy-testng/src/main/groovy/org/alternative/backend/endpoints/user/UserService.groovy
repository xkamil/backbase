package org.alternative.backend.endpoints.user

import org.alternative.backend.endpoints.user.getcurrentuser.UserGetCurrentUserEndpoint
import org.alternative.backend.endpoints.user.login.UserLoginEndpoint
import org.alternative.backend.endpoints.user.register.UserRegisterEndpoint

class UserService {

  private UserGetCurrentUserEndpoint getCurrentUserEndpoint
  @Lazy
  private UserRegisterEndpoint registerEndpoint = new UserRegisterEndpoint()
  @Lazy
  private UserLoginEndpoint loginEndpoint = new UserLoginEndpoint()

  UserService() {}

  UserService(String token) {
    getCurrentUserEndpoint = new UserGetCurrentUserEndpoint(token)
  }

  UserGetCurrentUserEndpoint getCurrentUser() { getCurrentUserEndpoint }

  UserRegisterEndpoint register() { registerEndpoint }

  UserLoginEndpoint login() { loginEndpoint }

}
