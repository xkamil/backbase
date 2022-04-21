package org.example.backend;

import static io.restassured.RestAssured.given;

import com.example.serum.api.filter.RequestHeaderFilter;
import com.example.serum.api.response.ApiJsonResponse;
import com.example.serum.api.response.ResponseStatusCode;
import io.restassured.specification.RequestSpecification;
import org.example.backend.api.ErrorResBody;
import org.example.backend.api.user.UserLoginReqBody;
import org.example.backend.api.user.UserRegisterReqBody;
import org.example.backend.api.user.UserResBody;
import org.example.backend.config.BackendConfig;
import org.example.backend.config.RequestSpec;
import org.example.backend.config.filter.RequestIdFilter;
import org.example.backend.model.api.articles.ArticleCreateReqBody;
import org.example.backend.model.api.articles.ArticleCreateRequest;
import org.example.backend.model.api.articles.ArticleDetailsRequest;
import org.example.backend.model.api.articles.ArticlesListQueryParams;
import org.example.backend.model.api.articles.ArticlesListRequest;
import org.example.backend.sampler.UserLoginSampler;

public class ApiClient {

  private final RequestSpecification requestSpec;
  private final RequestHeaderFilter authTokenFilter;
  private final RequestIdFilter requestIdFilter;

  public static class AuthTokenFilter extends RequestHeaderFilter {

    public AuthTokenFilter() {
      super("jwtauthorization");
    }
  }

  public ApiClient() {
    authTokenFilter = new AuthTokenFilter();
    requestIdFilter = new RequestIdFilter();

    requestSpec = RequestSpec.baseJsonRequestSpecBuilder()
        .setBaseUri(BackendConfig.API_URL)
        .addFilter(authTokenFilter)
        .addFilter(requestIdFilter)
        .build()
        .auth()
        .preemptive()
        .basic(BackendConfig.API_USERNAME, BackendConfig.API_PASSWORD);
  }

  public UserResBody authenticateUser(String email, String password) {
    var authOutput = loginUser(UserLoginSampler.fullInput(email, password)).assertOk();
    authTokenFilter.setValue("Token " + authOutput.user.token);
    return authOutput;
  }

  // api methods
  public ApiJsonResponse<UserResBody, ErrorResBody> registerUser(UserRegisterReqBody reqBody) {
    var req = given(requestSpec).body(reqBody).post("/api/users");
    return ApiJsonResponse.from(req, UserResBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

  public ApiJsonResponse<UserResBody, ErrorResBody> loginUser(UserLoginReqBody reqBody) {
    var req = given(requestSpec).body(reqBody).post("/api/users/login");
    return ApiJsonResponse.from(req, UserResBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

  public ApiJsonResponse<UserResBody, ErrorResBody> getCurrentUser() {
    return getCurrentUser(new ArticlesListQueryParams());
  }

  public ApiJsonResponse<UserResBody, ErrorResBody> getCurrentUser(ArticlesListQueryParams queryParams) {
    var req = given(requestSpec).queryParams(queryParams.asMap()).get("/api/user");
    return ApiJsonResponse.from(req, UserResBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

  public ArticleCreateRequest createArticle(ArticleCreateReqBody reqBody) {
    return new ArticleCreateRequest(requestSpec, reqBody);
  }

  public ArticleDetailsRequest getArticleDetails() {
    return new ArticleDetailsRequest(requestSpec);
  }

  public ArticlesListRequest getArticlesList() {
    return new ArticlesListRequest(requestSpec);
  }

}
