package org.example.backend.model.response

import pl.net.testit.serum.commons.json.JsonEntity

class ArticleDetailsResponseBody : JsonEntity() {
    var article: Article? = null

    class Article {
        var slug: String? = null
        var title: String? = null
        var description: String? = null
        var body: String? = null
        var tagList: List<String>? = null
        var createdAt: String? = null
        var updatedAt: String? = null
        var favorited: Boolean? = null
        var favoritesCount: Int? = null
        var author: Author? = null
    }

    class Author {
        var username: String? = null
        var bio: String? = null
        var image: String? = null
        var following: Boolean? = null
    }
}