package org.alternative.backend.endpoints.article.create

import pl.net.testit.serum.commons.json.JsonEntity

class ArticleCreateRequestBody extends JsonEntity {

  Article article

  static class Article {
    String title
    String description
    String body
    List<String> tagList
  }
}
