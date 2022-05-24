package org.alternative.backend.endpoints.user.register

import groovy.transform.InheritConstructors
import org.alternative.backend.base.ApiClient
import org.alternative.backend.base.Endpoint
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.ErrorResponseBody
import org.alternative.backend.endpoints.user.UserResponseBody

import static io.restassured.RestAssured.given

@InheritConstructors
class UserRegisterEndpoint extends ApiClient
    implements Endpoint<UserRegisterRequestBody, UserResponseBody, ErrorResponseBody> {

  @Override
  Response<UserResponseBody, ErrorResponseBody> execute(UserRegisterRequestBody body) {
    def response = given(requestSpec).body(body).post('/api/users')
    new Response(response, UserResponseBody, ErrorResponseBody, 200)
  }
}
