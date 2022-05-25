package org.alternative.backend

import groovy.transform.TypeChecked
import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification
import org.alternative.backend.base.config.filter.AuthTokenFilter
import org.alternative.backend.base.config.BackbaseRequestSpec
import org.alternative.backend.base.config.BackendConfig
import org.alternative.backend.endpoints.ApiClientHelperRequests
import org.alternative.backend.endpoints.article.ArticleRequests
import org.alternative.backend.endpoints.user.UserRequests
import pl.net.testit.serum.api.filter.RequestHeaderFilter

@TypeChecked
class ApiClient {

  private final RequestHeaderFilter authTokenFilter
  private final RequestSpecification requestSpec

  private final ApiClientHelperRequests apiClientHelperRequests
  private final UserRequests userRequests
  private final ArticleRequests articleRequests

  ApiClient() {
    authTokenFilter = new AuthTokenFilter()

    requestSpec = BackbaseRequestSpec.baseJsonRequestSpecBuilder()
        .setBaseUri(BackendConfig.API_URL)
        .addFilter(authTokenFilter)
        .addFilter(new RequestHeaderFilter('Request-Id').tap { value = "|${UUID.randomUUID()}." })
        .build()
        .auth()
        .preemptive()
        .basic(BackendConfig.API_USERNAME, BackendConfig.API_PASSWORD)

    apiClientHelperRequests = new ApiClientHelperRequests(this)
    userRequests = new UserRequests(this)
    articleRequests = new ArticleRequests(this)
  }

  RequestSpecification getRequestSpec(boolean withAuthentication = true){
    def specCopy = RestAssured.given(requestSpec) // you get copy of request spec so core request spec is never modified
    if (!withAuthentication) {
      specCopy.noFiltersOfType(AuthTokenFilter.class)
    }
    return specCopy
  }

  ApiClient setAuthToken(String token){
    authTokenFilter.setValue(token)
    this
  }

  ApiClientHelperRequests helperRequests() { return apiClientHelperRequests }

  UserRequests user() { return userRequests }

  ArticleRequests article() { return articleRequests }

}
