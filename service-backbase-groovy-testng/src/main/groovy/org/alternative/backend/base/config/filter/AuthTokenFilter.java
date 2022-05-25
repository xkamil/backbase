package org.alternative.backend.base.config.filter;

import pl.net.testit.serum.api.filter.RequestHeaderFilter;

// this is created as separate class so it can be excluded from request spec
// rest assured allows excluding filters by class and not instance
public class AuthTokenFilter extends RequestHeaderFilter {

  public AuthTokenFilter() {
    super("jwtauthorization");
  }
}
