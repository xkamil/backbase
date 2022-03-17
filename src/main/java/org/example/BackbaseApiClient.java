package org.example;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.example.config.BackbaseConfig;
import org.example.config.ffilter.AuthTokenFilter;
import org.example.config.ffilter.RequestLoggingFilter;
import org.example.config.ffilter.ResponseLoggingFilter;
import org.example.model.request.ApiResponse;
import org.example.model.request.ArticleCreateReqBody;
import org.example.model.request.UserLoginReqBody;
import org.example.model.request.UserRegisterReqBody;
import org.example.model.response.ArticleResBody;
import org.example.model.response.UserResBody;
import org.example.sampler.UserLoginSampler;

public class BackbaseApiClient {

  private static final String ARTICLES = "/api/articles";
  private static final String USERS = "/api/users";
  private static final String USER = "/api/user";
  private static final String USERS_LOGIN = "/api/users/login";

  private final RequestSpecification requestSpec;
  private final AuthTokenFilter authTokenFilter;

  public BackbaseApiClient() {
    authTokenFilter = new AuthTokenFilter();
    final var reqFilter = new RequestLoggingFilter();
    final var resFilter = new ResponseLoggingFilter();

    requestSpec = new RequestSpecBuilder()
        .addFilter(reqFilter)
        .addFilter(resFilter)
        .addFilter(authTokenFilter)
        .setBaseUri(BackbaseConfig.API_URL)
        .setContentType(ContentType.APPLICATION_JSON.toString())
        .setRelaxedHTTPSValidation()
        .build()
        .auth()
        .preemptive()
        .basic(BackbaseConfig.API_USERNAME, BackbaseConfig.API_PASSWORD);
  }

  public UserResBody authenticateUser(String email, String password) {
    var authOutput = loginUser(UserLoginSampler.fullInput(email, password)).ok();
    authTokenFilter.setToken(authOutput.user.token);
    return authOutput;
  }

  // api methods

  public ApiResponse<UserResBody> registerUser(UserRegisterReqBody reqBody) {
    return new ApiResponse<>(given(requestSpec).body(reqBody).post(USERS), UserResBody.class);
  }

  public ApiResponse<UserResBody> loginUser(UserLoginReqBody reqBody) {
    return new ApiResponse<>(given(requestSpec).body(reqBody).post(USERS_LOGIN), UserResBody.class);
  }

  public ApiResponse<UserResBody> getCurrentUser() {
    return new ApiResponse<>(given(requestSpec).get(USER), UserResBody.class);
  }

  public ApiResponse<ArticleResBody> createArticle(ArticleCreateReqBody reqBody) {
    return new ApiResponse<>(given(requestSpec).body(reqBody).post(ARTICLES), ArticleResBody.class);
  }

}
