package org.alternative.backend.endpoints.article

import io.restassured.specification.RequestSpecification
import org.alternative.backend.ApiClient
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.ErrorResponseBody
import org.alternative.backend.endpoints.article.create.ArticleCreateRequestBody
import org.alternative.backend.endpoints.article.create.ArticleCreateResponseBody
import org.alternative.backend.endpoints.article.list.ArticlesListQueryParams
import org.alternative.backend.endpoints.article.list.ArticlesListResponseBody

import static io.restassured.RestAssured.given

class ArticleRequests {

  private final ApiClient apiClient
  private final RequestSpecification requestSpec

  ArticleRequests(ApiClient apiClient) {
    this.apiClient = apiClient
    this.requestSpec = apiClient.getRequestSpec()
  }

  Response<ArticleCreateResponseBody, ErrorResponseBody> create(ArticleCreateRequestBody body) {
    def response = given(requestSpec).body(body).post('/api/articles')
    new Response(response, ArticleCreateResponseBody, ErrorResponseBody, 200)
  }

  Response<ArticlesListResponseBody, ErrorResponseBody> list(ArticlesListQueryParams queryParams = new ArticlesListQueryParams()) {
    def response = given(requestSpec).queryParams(queryParams.asMap()).get('/api/articles')
    new Response(response, ArticlesListResponseBody, ErrorResponseBody, 200)
  }
}
