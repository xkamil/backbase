package org.example.backend.model.response

class ArticleDetailsResponseBody(
  val article: ArticleDetailsResponseBodyArticle? = null,
)

class ArticleDetailsResponseBodyArticle(
  val slug: String? = null,
  val title: String? = null,
  val description: String? = null,
  val body: String? = null,
  val tagList: List<String>? = null,
  val createdAt: String? = null,
  val updatedAt: String? = null,
  val favorited: Boolean? = null,
  val favoritesCount: Int? = null,
  val author: ArticleDetailsResponseBodyArticleAuthor? = null,
)

class ArticleDetailsResponseBodyArticleAuthor(
  val username: String? = null,
  val bio: String? = null,
  val image: String? = null,
  val following: Boolean? = null,
)