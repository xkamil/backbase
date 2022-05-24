package org.alternative.backend.flows

import groovy.transform.TypeChecked
import org.alternative.backend.endpoints.user.UserRequests
import org.alternative.backend.endpoints.user.login.UserLoginRequestBody

import static org.alternative.backend.base.EnsureResponse.extract

@TypeChecked
class TokenProvider {

  private static UserRequests USER_SERVICE = new UserRequests()

  static String provideToken(String email, String password) {
    def request = new UserLoginRequestBody(
        user: new UserLoginRequestBody.User(email: email, password: password)
    )
    def response = USER_SERVICE.login(request)
    extract(response) { response.parse().user.token }
  }
}
