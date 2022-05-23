package org.example.backend

import io.restassured.specification.RequestSpecification
import org.example.backend.config.BackbaseRequestSpec
import org.example.backend.config.BackendConfig
import org.example.backend.config.filter.AuthTokenFilter
import org.example.backend.config.filter.RequestIdFilter
import org.example.backend.model.request.ArticleCreateReqBody
import org.example.backend.model.request.UserLoginReqBody
import org.example.backend.model.request.UserRegisterReqBody
import org.example.backend.model.response.ArticleDetailsResponseBody
import org.example.backend.model.response.ArticlesListResponseBody
import org.example.backend.model.response.ErrorResBody
import org.example.backend.model.response.UserResBody
import org.example.backend.sampler.UserLoginSampler
import pl.net.testit.serum.api.filter.RequestHeaderFilter
import pl.net.testit.serum.api.filter.RequestQueryParamsFilter
import pl.net.testit.serum.api.response.ApiJsonResponse

import static io.restassured.RestAssured.given
import static pl.net.testit.serum.api.response.ApiJsonResponse.from
import static pl.net.testit.serum.api.response.ResponseStatusCode.OK

class BackbaseApiClient {

  private final RequestHeaderFilter authTokenFilter
  private final RequestQueryParamsFilter queryParamsFilter
  private final RequestSpecification requestSpec

  BackbaseApiClient() {
    authTokenFilter = new AuthTokenFilter()
    queryParamsFilter = new RequestQueryParamsFilter()
    requestSpec = BackbaseRequestSpec.baseJsonRequestSpecBuilder()
        .setBaseUri(BackendConfig.API_URL)
        .addFilter(authTokenFilter)
        .addFilter(queryParamsFilter)
        .addFilter(new RequestIdFilter())
        .build()
        .auth()
        .preemptive()
        .basic(BackendConfig.API_USERNAME, BackendConfig.API_PASSWORD)
  }

  // api client helper method starts with _dash
  void _authenticateUser(String email, String password) {
    def authOutput = loginUser(UserLoginSampler.fullInput(email, password)).assertOk()
    authTokenFilter.setValue("Token " + authOutput.user.token)
  }

  BackbaseApiClient withQueryParams(Map<String, String> queryParams) {
    queryParamsFilter.setQueryParamsForNextRequest(queryParams)
    this
  }

  // api methods

  ApiJsonResponse<UserResBody, ErrorResBody> registerUser(UserRegisterReqBody reqBody) {
    def response = given(requestSpec).body(reqBody).post('/api/users')
    from(response, UserResBody, OK, ErrorResBody)
  }

  ApiJsonResponse<UserResBody, ErrorResBody> loginUser(UserLoginReqBody reqBody) {
    def response = given(requestSpec).body(reqBody).post('/api/users/login')
    from(response, UserResBody, OK, ErrorResBody)
  }

  ApiJsonResponse<UserResBody, ErrorResBody> getCurrentUser() {
    def response = given(requestSpec).get('/api/user')
    from(response, UserResBody, OK, ErrorResBody)
  }

  ApiJsonResponse<ArticleDetailsResponseBody, ErrorResBody> createArticle(ArticleCreateReqBody reqBody) {
    def response = given(requestSpec).body(reqBody).post('/api/articles')
    from(response, ArticleDetailsResponseBody, OK, ErrorResBody)
  }

  ApiJsonResponse<ArticlesListResponseBody, ErrorResBody> getArticlesList() {
    def response = given(requestSpec).get('/api/articles')
    from(response, ArticlesListResponseBody, OK, ErrorResBody)
  }

}
