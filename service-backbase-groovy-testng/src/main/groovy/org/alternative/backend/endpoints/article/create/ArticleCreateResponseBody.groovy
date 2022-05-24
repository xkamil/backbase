package org.alternative.backend.endpoints.article.create

import pl.net.testit.serum.commons.json.JsonEntity

class ArticleCreateResponseBody extends JsonEntity {

  Article article

  static class Article {
    String slug
    String title
    String description
    String body
    List<String> tagList
    String createdAt
    String updatedAt
    Boolean favorited
    Integer favoritesCount
    Author author
  }

  static class Author {
    String username
    String bio
    String image
    Boolean following
  }
}
