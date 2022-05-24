package org.alternative.backend.endpoints.article

import org.alternative.backend.endpoints.article.create.ArticleCreateEndpoint
import org.alternative.backend.endpoints.article.list.ArticleListEndpoint

class ArticleService {

  private ArticleCreateEndpoint createEndpoint
  private ArticleListEndpoint listEndpoint

  ArticleService(String token) {
    createEndpoint = new ArticleCreateEndpoint(token)
    listEndpoint = new ArticleListEndpoint(token)
  }

  ArticleCreateEndpoint create() { createEndpoint }

  ArticleListEndpoint list() { listEndpoint }
}
