package org.example.backend.config.filter;

import com.example.serum.api.filter.RequestHeaderFilter;
import java.util.UUID;

public class RequestIdFilter extends RequestHeaderFilter {

  public static final String HEADER_NAME = "Request-Id";

  public RequestIdFilter() {
    super(HEADER_NAME);
    this.setValue(() -> "|" + UUID.randomUUID() + ".");
  }
}
