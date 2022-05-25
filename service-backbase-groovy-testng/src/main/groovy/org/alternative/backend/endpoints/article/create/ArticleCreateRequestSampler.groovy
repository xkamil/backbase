package org.alternative.backend.endpoints.article.create


import static org.alternative.backend.base.DataGenerator.generateUuidString

class ArticleCreateRequestSampler  {

  ArticleCreateRequestBody minimal() {
    new ArticleCreateRequestBody().tap {
      article = new ArticleCreateRequestBody.Article().tap {
        title = "Title ${generateUuidString()}"
        description = "Description ${generateUuidString()}"
        body = "Body ${generateUuidString()}"
      }
    }
  }

  ArticleCreateRequestBody full() {
    minimal().tap {
      article.tagList = [generateUuidString(), generateUuidString()]
    }
  }
}
