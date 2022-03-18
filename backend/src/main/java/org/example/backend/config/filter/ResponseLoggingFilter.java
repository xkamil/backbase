package org.example.backend.config.filter;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.util.List;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResponseLoggingFilter implements OrderedFilter {

  private static final Logger log = LoggerFactory.getLogger(ResponseLoggingFilter.class);
  private static final List<String> PRINT_CONTENT_TYPES_BODY = List
      .of(ContentType.APPLICATION_JSON.toString().toLowerCase());

  @Override
  public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
    var response = ctx.next(req, res);

    var sb = new StringBuffer();
    sb.append(response.getStatusLine()).append("\n");
    sb.append("\n");
    response.getHeaders().asList().forEach(header -> {
      sb.append(header.getName()).append(": ").append(header.getValue()).append("\n");
    });

    if (PRINT_CONTENT_TYPES_BODY.contains(response.getContentType().toLowerCase())) {
      sb.append(response.getBody().asPrettyString()).append("\n");
    } else {
      sb.append("Body in logs hidden for content type").append(response.getContentType());
    }

    log.info("Response >>>>>>>>>>>>> \n {}", sb.toString());
    return response;
  }

  @Override
  public int getOrder() {
    return Integer.MAX_VALUE;
  }
}
