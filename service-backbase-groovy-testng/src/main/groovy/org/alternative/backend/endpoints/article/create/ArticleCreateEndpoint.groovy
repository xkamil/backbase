package org.alternative.backend.endpoints.article.create

import groovy.transform.InheritConstructors
import org.alternative.backend.base.Endpoint
import org.alternative.backend.base.Response
import org.alternative.backend.endpoints.AuthenticatedEndpoint
import org.alternative.backend.endpoints.ErrorResponseBody

import static io.restassured.RestAssured.given

@InheritConstructors
class ArticleCreateEndpoint extends AuthenticatedEndpoint
    implements Endpoint<ArticleCreateRequestBody, ArticleCreateResponseBody, ErrorResponseBody> {

  @Override
  Response<ArticleCreateResponseBody, ErrorResponseBody> execute(ArticleCreateRequestBody body) {
    def response = given(requestSpec).body(body).post('/api/articles')
    new Response(response, ArticleCreateResponseBody, ErrorResponseBody, 200)
  }
}
