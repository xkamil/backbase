package org.alternative.backend.endpoints.article.list

import groovy.transform.InheritConstructors
import org.alternative.backend.base.Endpoint
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.AuthenticatedEndpoint
import org.alternative.backend.endpoints.ErrorResponseBody

import static io.restassured.RestAssured.given

@InheritConstructors
class ArticleListEndpoint extends AuthenticatedEndpoint
    implements Endpoint<ArticlesListQueryParams, ArticlesListResponseBody, ErrorResponseBody> {

  @Override
  Response<ArticlesListResponseBody, ErrorResponseBody> execute(ArticlesListQueryParams queryParams = null) {
    if (queryParams) {
      withQueryParams(queryParams.asMap())
    }
    def response = given(requestSpec).get('/api/articles')
    new Response(response, ArticlesListResponseBody, ErrorResponseBody, 200)
  }
}
