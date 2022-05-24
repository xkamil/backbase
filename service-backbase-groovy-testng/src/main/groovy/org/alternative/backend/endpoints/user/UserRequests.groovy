package org.alternative.backend.endpoints.user

import groovy.transform.InheritConstructors
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.AuthenticatedEndpoint
import org.alternative.backend.endpoints.ErrorResponseBody
import org.alternative.backend.endpoints.user.login.UserLoginRequestBody
import org.alternative.backend.endpoints.user.register.UserRegisterRequestBody

import static io.restassured.RestAssured.given

@InheritConstructors
class UserRequests extends AuthenticatedEndpoint {

  Response<UserResponseBody, ErrorResponseBody> getCurrentUser() {
    def response = given(requestSpec).get('/api/user')
    new Response(response, UserResponseBody, ErrorResponseBody, 200)
  }

  Response<UserResponseBody, ErrorResponseBody> register(UserRegisterRequestBody body) {
    def response = given(requestSpec).body(body).post('/api/users')
    new Response(response, UserResponseBody, ErrorResponseBody, 200)
  }

  Response<UserResponseBody, ErrorResponseBody> login(UserLoginRequestBody body) {
    def response = given(requestSpec).body(body).post('/api/users/login')
    new Response(response, UserResponseBody, ErrorResponseBody, 200)
  }

}
