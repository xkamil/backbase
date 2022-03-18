package org.example.backend.config.filter;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestLoggingFilter implements OrderedFilter {

  private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

  @Override
  public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
    var sb = new StringBuffer();
    sb.append(req.getMethod()).append(" ").append(req.getURI()).append("\n");
    req.getHeaders().asList().forEach(header -> {
      sb.append(header.getName()).append(": ").append(header.getValue()).append("\n");
    });

    if (req.getBody() != null) {
      sb.append(req.getBody().toString()).append("\n");
    }

    log.info("Request >>>>>>>>>>>>> \n{}", sb.toString());

    return ctx.next(req, res);
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }
}
