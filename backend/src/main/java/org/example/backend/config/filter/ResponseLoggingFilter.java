package org.example.backend.config.filter;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;


public class ResponseLoggingFilter implements OrderedFilter {

  @Override
  public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
    var response = ctx.next(req, res);

    System.out.println(response.getStatusLine());

    response.getHeaders().asList().forEach(header -> {
      System.out.println(header.getName() + ": " + header.getValue());
    });

    response.getBody().prettyPrint();

    return response;
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }
}
