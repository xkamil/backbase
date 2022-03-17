package org.example.config.ffilter;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.util.Optional;


public class AuthTokenFilter implements Filter {

  private static final String AUTH_HEADER = "jwtauthorization";

  private String token;

  public void setToken(String token) {
    this.token = "Token " + token;
  }

  public void removeToken() {
    this.token = null;
  }

  @Override
  public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
    var reqHeaders = req.getHeaders();
    req.removeHeaders();

    reqHeaders.asList().stream()
        .filter(h -> !h.getName().equalsIgnoreCase(AUTH_HEADER))
        .forEach(req::header);

    Optional.ofNullable(token).ifPresent(token -> req.header(AUTH_HEADER, token));

    return ctx.next(req, res);
  }
}
