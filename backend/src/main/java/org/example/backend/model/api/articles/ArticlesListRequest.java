package org.example.backend.model.api.articles;

import com.example.serum.api.response.ApiJsonResponse;
import com.example.serum.api.response.ResponseStatusCode;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.backend.api.ErrorResBody;

@Setter
@Accessors(fluent = true)
public class ArticlesListRequest extends BaseRequest<ArticlesListRequest> {

  private ArticlesListQueryParams queryParams;

  public ArticlesListRequest(RequestSpecification requestSpec) {
    super(requestSpec);
    queryParams = new ArticlesListQueryParams();
  }

  @Override
  public ApiJsonResponse<ArticlesListResponseBody, ErrorResBody> execute() {
    var req = getRequestSpec()
        .queryParams(queryParams.asMap())
        .get("/api/articles");

    return ApiJsonResponse.from(req, ArticlesListResponseBody.class, ResponseStatusCode.OK, ErrorResBody.class);
  }
}
