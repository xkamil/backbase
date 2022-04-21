package org.example.backend.model.api.articles;

import com.example.serum.api.response.ApiJsonResponse;
import com.example.serum.api.response.ResponseStatusCode;
import io.restassured.specification.RequestSpecification;
import org.example.backend.api.ErrorResBody;

public class ArticleCreateRequest extends BaseRequest<ArticleCreateRequest> {

  private final ArticleCreateReqBody reqBody;

  public ArticleCreateRequest(RequestSpecification requestSpec, ArticleCreateReqBody reqBody) {
    super(requestSpec);
    this.reqBody = reqBody;
  }

  @Override
  public ApiJsonResponse<ArticleDetailsResponseBody, ErrorResBody> execute() {
    var req = getRequestSpec()
        .body(reqBody.toString())
        .post("/api/articles");

    return ApiJsonResponse.from(req, ArticleDetailsResponseBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }

}
