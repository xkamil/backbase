package org.alternative.backend.endpoints.article

import groovy.transform.InheritConstructors
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.AuthenticatedEndpoint
import org.alternative.backend.endpoints.ErrorResponseBody
import org.alternative.backend.endpoints.article.create.ArticleCreateRequestBody
import org.alternative.backend.endpoints.article.create.ArticleCreateResponseBody
import org.alternative.backend.endpoints.article.list.ArticlesListQueryParams
import org.alternative.backend.endpoints.article.list.ArticlesListResponseBody

import static io.restassured.RestAssured.given

@InheritConstructors
class ArticleRequests extends AuthenticatedEndpoint {

  Response<ArticleCreateResponseBody, ErrorResponseBody> create(ArticleCreateRequestBody body) {
    def response = given(requestSpec).body(body).post('/api/articles')
    new Response(response, ArticleCreateResponseBody, ErrorResponseBody, 200)
  }

  Response<ArticlesListResponseBody, ErrorResponseBody> list(ArticlesListQueryParams queryParams = null) {
    if (queryParams) {
      withQueryParams(queryParams.asMap())
    }
    def response = given(requestSpec).get('/api/articles')
    new Response(response, ArticlesListResponseBody, ErrorResponseBody, 200)
  }
}
