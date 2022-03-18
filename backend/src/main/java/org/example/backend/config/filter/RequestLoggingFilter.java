package org.example.backend.config.filter;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;


public class RequestLoggingFilter implements OrderedFilter {

  @Override
  public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
    System.out.println(req.getMethod() + " " + req.getURI());

    req.getHeaders().asList().forEach(header -> {
      System.out.println(header.getName() + ": " + header.getValue());
    });

    if (req.getBody() != null) {
      System.out.println(req.getBody().toString() + "\n");
    }

    return ctx.next(req, res);
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }
}
