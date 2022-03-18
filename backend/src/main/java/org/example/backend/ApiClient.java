package org.example.backend;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.example.backend.config.BackendConfig;
import org.example.backend.config.filter.AuthTokenFilter;
import org.example.backend.config.filter.RequestLoggingFilter;
import org.example.backend.config.filter.ResponseLoggingFilter;
import org.example.backend.model.request.ArticleCreateReqBody;
import org.example.backend.model.request.UserLoginReqBody;
import org.example.backend.model.request.UserRegisterReqBody;
import org.example.backend.model.response.ApiResponse;
import org.example.backend.model.response.ArticleResBody;
import org.example.backend.model.response.UserResBody;
import org.example.backend.sampler.UserLoginSampler;

public class ApiClient {

  private static final String ARTICLES = "/api/articles";
  private static final String USERS = "/api/users";
  private static final String USER = "/api/user";
  private static final String USERS_LOGIN = "/api/users/login";

  private final RequestSpecification requestSpec;
  private final AuthTokenFilter authTokenFilter;

  public ApiClient() {
    authTokenFilter = new AuthTokenFilter("jwtauthorization");

    requestSpec = new RequestSpecBuilder()
        .addFilter(new RequestLoggingFilter())
        .addFilter(new ResponseLoggingFilter())
        .addFilter(authTokenFilter)
        .setBaseUri(BackendConfig.API_URL)
        .setContentType(ContentType.APPLICATION_JSON.toString())
        .setRelaxedHTTPSValidation()
        .build()
        .auth()
        .preemptive()
        .basic(BackendConfig.API_USERNAME, BackendConfig.API_PASSWORD);
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
