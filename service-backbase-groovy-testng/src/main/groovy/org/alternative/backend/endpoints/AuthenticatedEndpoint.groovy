package org.alternative.backend.endpoints

import org.alternative.backend.base.ApiClient

abstract class AuthenticatedEndpoint extends ApiClient {

  AuthenticatedEndpoint() { }

  AuthenticatedEndpoint(String token) {
    authenticate(token)
  }
}
