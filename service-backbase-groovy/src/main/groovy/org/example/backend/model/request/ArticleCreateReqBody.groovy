package org.example.backend.model.request

import pl.net.testit.serum.commons.json.JsonEntity

class ArticleCreateReqBody extends JsonEntity {

  Article article

  static class Article {
    String title
    String description
    String body
    List<String> tagList
  }
}
