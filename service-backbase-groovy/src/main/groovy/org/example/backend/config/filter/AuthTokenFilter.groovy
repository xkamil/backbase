package org.example.backend.config.filter

import pl.net.testit.serum.api.filter.RequestHeaderFilter

class AuthTokenFilter extends RequestHeaderFilter {

  public static final String HEADER_NAME = 'jwtauthorization'

  AuthTokenFilter() {
    super(HEADER_NAME)
  }
}
