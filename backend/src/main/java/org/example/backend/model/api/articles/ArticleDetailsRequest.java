package org.example.backend.model.api.articles;

import static io.restassured.RestAssured.given;

import com.example.serum.api.response.ApiJsonResponse;
import com.example.serum.api.response.ResponseStatusCode;
import io.restassured.specification.RequestSpecification;

public class ArticleDetailsRequest {

  private final RequestSpecification requestSpec;

  public ArticleDetailsRequest(RequestSpecification requestSpec) {
    this.requestSpec = requestSpec;
  }

  public ApiJsonResponse<ArticleDetailsResponseBody, String> sendRequest(String slug) {
    var req = given(requestSpec)
        .pathParam("slug", slug)
        .get("/api/articles/{slug}");

    return ApiJsonResponse.from(req, ArticleDetailsResponseBody.class, ResponseStatusCode.OK, String.class);
  }
}
