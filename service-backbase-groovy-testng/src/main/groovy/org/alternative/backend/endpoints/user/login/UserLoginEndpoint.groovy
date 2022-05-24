package org.alternative.backend.endpoints.user.login

import groovy.transform.InheritConstructors
import org.alternative.backend.base.ApiClient
import org.alternative.backend.base.Endpoint
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.ErrorResponseBody
import org.alternative.backend.endpoints.user.UserResponseBody

import static io.restassured.RestAssured.given

@InheritConstructors
class UserLoginEndpoint extends ApiClient
    implements Endpoint<UserLoginRequestBody, UserResponseBody, ErrorResponseBody> {

  @Override
  Response<UserResponseBody, ErrorResponseBody> execute(UserLoginRequestBody body) {
    def response = given(requestSpec).body(body).post('/api/users/login')
    new Response(response, UserResponseBody, ErrorResponseBody, 200)
  }
}
