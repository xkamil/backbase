package org.alternative.backend.endpoints.user

import io.restassured.specification.RequestSpecification
import org.alternative.backend.ApiClient
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.ErrorResponseBody
import org.alternative.backend.endpoints.user.login.UserLoginRequestBody
import org.alternative.backend.endpoints.user.register.UserRegisterRequestBody

import static io.restassured.RestAssured.given

class UserRequests  {

  private final ApiClient apiClient
  private final RequestSpecification requestSpec

  UserRequests(ApiClient apiClient) {
    this.apiClient = apiClient
    this.requestSpec = apiClient.getRequestSpec()
  }

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
