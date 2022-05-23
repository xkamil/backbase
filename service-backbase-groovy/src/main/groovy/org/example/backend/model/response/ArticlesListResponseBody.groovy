package org.example.backend.model.response

class ArticlesListResponseBody {

  List<ArticleView> articles
  Integer articlesCount

  static class ArticleView {
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

    static class Author {
      String username
      String bio
      String image
      Boolean following
    }
  }
}
