package org.example.backend.model.api.articles;

import static io.restassured.RestAssured.given;

import com.example.serum.api.filter.RequestHeaderFilter;
import com.example.serum.api.response.ApiJsonResponse;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;

public abstract class BaseRequest<SubClass> {

  private final RequestSpecification requestSpec;

  protected BaseRequest(RequestSpecification requestSpec) {
    this.requestSpec = given(requestSpec);
  }

  public RequestSpecification getRequestSpec() {
    return requestSpec;
  }

  /**
   * Set header value in request This method override header if it's exists.
   *
   * @param headerName - header name
   * @param value - header value
   * @return this
   */
  @SuppressWarnings("unchecked")
  public SubClass setHeader(String headerName, String value) {
    var reqHeaderFilter = new RequestHeaderFilter(headerName);
    reqHeaderFilter.setValue(value);
    requestSpec.filter(reqHeaderFilter);
    return (SubClass) this;
  }

  /**
   * Remove existing header from request. If header is not present in request then it does nothing.
   *
   * @param headerName - header name case insensitive
   * @return this
   */
  @SuppressWarnings("unchecked")
  public SubClass removeHeader(String headerName) {
    var reqHeaderFilter = new RequestHeaderFilter(headerName);
    requestSpec.filter(reqHeaderFilter);
    return (SubClass) this;
  }

  @SuppressWarnings("unchecked")
  public SubClass removeFilter(Class<? extends Filter> filterClass) {
    requestSpec.noFiltersOfType(filterClass);
    return (SubClass) this;
  }


  /**
   * Execute request and returns reponse
   *
   * @return ApiJsonResponse
   */
  public abstract ApiJsonResponse<?, ?> execute();

}
