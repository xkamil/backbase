package org.example.backend.model.response

class ArticlesListResponseBody {
    var articles: List<ArticleView>? = null
    var articlesCount: Int? = null

    class ArticleView {
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

        class Author {
            var username: String? = null
            var bio: String? = null
            var image: String? = null
            var following: Boolean? = null
        }
    }
}