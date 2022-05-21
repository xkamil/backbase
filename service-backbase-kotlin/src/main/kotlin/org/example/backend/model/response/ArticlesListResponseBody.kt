package org.example.backend.model.response

class ArticlesListResponseBody(
        val articles: List<ArticlesListArticle>? = null,
        val articlesCount: Int? = null,
)

class ArticlesListArticle(
        val slug: String? = null,
        val title: String? = null,
        val description: String? = null,
        val body: String? = null,
        val tagList: List<String>? = null,
        val createdAt: String? = null,
        val updatedAt: String? = null,
        val favorited: Boolean? = null,
        val favoritesCount: Int? = null,
        val author: ArticlesListArticleAuthor? = null,
)

class ArticlesListArticleAuthor(
        val username: String? = null,
        val bio: String? = null,
        val image: String? = null,
        val following: Boolean? = null,
)