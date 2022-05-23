package org.example.backend;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;
import java.util.Map;
import org.example.backend.config.BackbaseRequestSpec;
import org.example.backend.config.BackendConfig;
import org.example.backend.config.filter.AuthTokenFilter;
import org.example.backend.config.filter.RequestIdFilter;
import org.example.backend.model.request.ArticleCreateReqBody;
import org.example.backend.model.request.UserLoginReqBody;
import org.example.backend.model.request.UserRegisterReqBody;
import org.example.backend.model.response.ArticleDetailsResponseBody;
import org.example.backend.model.response.ArticlesListResponseBody;
import org.example.backend.model.response.ErrorResBody;
import org.example.backend.model.response.UserResBody;
import org.example.backend.sampler.UserLoginSampler;
import pl.net.testit.serum.api.filter.RequestHeaderFilter;
import pl.net.testit.serum.api.filter.RequestQueryParamsFilter;
import pl.net.testit.serum.api.response.ApiJsonResponse;
import pl.net.testit.serum.api.response.ResponseStatusCode;

public class BackbaseApiClient {

  private final RequestHeaderFilter authTokenFilter;
  private final RequestQueryParamsFilter queryParamsFilter;
  private final RequestSpecification requestSpec;

  public BackbaseApiClient() {
    authTokenFilter = new AuthTokenFilter();
    queryParamsFilter = new RequestQueryParamsFilter();
    requestSpec = BackbaseRequestSpec.baseJsonRequestSpecBuilder()
        .setBaseUri(BackendConfig.API_URL)
        .addFilter(authTokenFilter)
        .addFilter(queryParamsFilter)
        .addFilter(new RequestIdFilter())
        .build()
        .auth()
        .preemptive()
        .basic(BackendConfig.API_USERNAME, BackendConfig.API_PASSWORD);
  }

  // api client helper method starts with _dash
  public void _authenticateUser(String email, String password) {
    var authOutput = loginUser(UserLoginSampler.fullInput(email, password)).assertOk();
    authTokenFilter.setValue("Token " + authOutput.user.token);
  }

  public BackbaseApiClient withQueryParams(Map<String, String> queryParams) {
    queryParamsFilter.setQueryParamsForNextRequest(queryParams);
    return this;
  }

  // api methods

  public ApiJsonResponse<UserResBody, ErrorResBody> registerUser(UserRegisterReqBody reqBody) {
    var response = given(requestSpec).body(reqBody).post("/api/users");
    return ApiJsonResponse.from(response, UserResBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

  public ApiJsonResponse<UserResBody, ErrorResBody> loginUser(UserLoginReqBody reqBody) {
    var response = given(requestSpec).body(reqBody).post("/api/users/login");
    return ApiJsonResponse.from(response, UserResBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

  public ApiJsonResponse<UserResBody, ErrorResBody> getCurrentUser() {
    var response = given(requestSpec).get("/api/user");
    return ApiJsonResponse.from(response, UserResBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

  public ApiJsonResponse<ArticleDetailsResponseBody, ErrorResBody> createArticle(ArticleCreateReqBody reqBody) {
    var response = given(requestSpec).body(reqBody).post("/api/articles");
    return ApiJsonResponse.from(response, ArticleDetailsResponseBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

  public ApiJsonResponse<ArticlesListResponseBody, ErrorResBody> getArticlesList() {
    var response = given(requestSpec).get("/api/articles");
    return ApiJsonResponse.from(response, ArticlesListResponseBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

}
