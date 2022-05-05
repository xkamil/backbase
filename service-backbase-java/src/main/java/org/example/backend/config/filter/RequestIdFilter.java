package org.example.backend.config.filter;

import java.util.UUID;
import pl.net.testit.serum.api.filter.RequestHeaderFilter;

public class RequestIdFilter extends RequestHeaderFilter {

  public static final String HEADER_NAME = "Request-Id";

  public RequestIdFilter() {
    super(HEADER_NAME);
    this.setValue(() -> "|" + UUID.randomUUID() + ".");
  }
}
