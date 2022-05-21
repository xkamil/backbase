package org.example.backend.model.request

class ArticleCreateReqBody(
        var article: ArticleCreateReqBodyArticle?
)

class ArticleCreateReqBodyArticle(

        var title: String?,
        var description: String?,
        var body: String?,
        var tagList: List<String>? = null
)
