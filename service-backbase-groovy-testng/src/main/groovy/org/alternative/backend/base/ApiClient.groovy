package org.alternative.backend.base

import groovy.transform.TypeChecked
import io.restassured.specification.RequestSpecification
import org.alternative.backend.base.config.BackbaseRequestSpec
import org.alternative.backend.base.config.BackendConfig
import pl.net.testit.serum.api.filter.RequestHeaderFilter
import pl.net.testit.serum.api.filter.RequestQueryParamsFilter

@TypeChecked
class ApiClient {

  protected final RequestHeaderFilter authTokenFilter
  protected final RequestQueryParamsFilter queryParamsFilter
  protected final RequestSpecification requestSpec

  ApiClient() {
    authTokenFilter = new RequestHeaderFilter('jwtauthorization')
    queryParamsFilter = new RequestQueryParamsFilter()
    requestSpec = BackbaseRequestSpec.baseJsonRequestSpecBuilder()
        .setBaseUri(BackendConfig.API_URL)
        .addFilter(authTokenFilter)
        .addFilter(queryParamsFilter)
        .addFilter(new RequestHeaderFilter('Request-Id').tap { value = "|${UUID.randomUUID()}." })
        .build()
        .auth()
        .preemptive()
        .basic(BackendConfig.API_USERNAME, BackendConfig.API_PASSWORD)
  }

  ApiClient authenticate(String token) {
    authTokenFilter.setValue("Token $token")
    this
  }

  ApiClient withQueryParams(Map<String, String> queryParams) {
    queryParamsFilter.setQueryParamsForNextRequest(queryParams)
    this
  }

}
