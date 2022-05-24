package org.alternative.backend.endpoints.user.getcurrentuser

import groovy.transform.InheritConstructors
import org.alternative.backend.base.Endpoint
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.AuthenticatedEndpoint
import org.alternative.backend.endpoints.ErrorResponseBody
import org.alternative.backend.endpoints.user.UserResponseBody

import static io.restassured.RestAssured.given

@InheritConstructors
class UserGetCurrentUserEndpoint extends AuthenticatedEndpoint
    implements Endpoint<Object, UserResponseBody, ErrorResponseBody> {

  @Override
  Response<UserResponseBody, ErrorResponseBody> execute(Object ignored = null) {
    def response = given(requestSpec).get('/api/user')
    new Response(response, UserResponseBody, ErrorResponseBody, 200)
  }
}
