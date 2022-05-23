package org.example.backend.config.filter

import pl.net.testit.serum.api.filter.RequestHeaderFilter

class RequestIdFilter extends RequestHeaderFilter {

  public static final String HEADER_NAME = 'Request-Id'

  RequestIdFilter() {
    super(HEADER_NAME)
    setValue { "|${UUID.randomUUID()}." as String }
  }
}
