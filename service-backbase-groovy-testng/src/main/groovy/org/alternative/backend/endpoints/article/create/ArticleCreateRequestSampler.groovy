package org.alternative.backend.endpoints.article.create

import org.alternative.backend.base.RequestSampler

import static org.alternative.backend.base.DataGenerator.generateUuidString

class ArticleCreateRequestSampler implements RequestSampler<ArticleCreateRequestBody> {

  @Override
  ArticleCreateRequestBody minimal() {
    new ArticleCreateRequestBody().tap {
      article = new ArticleCreateRequestBody.Article().tap {
        title = "Title ${generateUuidString()}"
        description = "Description ${generateUuidString()}"
        body = "Body ${generateUuidString()}"
      }
    }
  }

  @Override
  ArticleCreateRequestBody full() {
    minimal().tap {
      article.tagList = [generateUuidString(), generateUuidString()]
    }
  }
}
