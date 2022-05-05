package org.example.backend.model.request

import pl.net.testit.serum.commons.json.JsonEntity

class ArticleCreateReqBody(
        var article: Article?
) : JsonEntity()

class Article(
        var title: String?,
        var description: String?,
        var body: String?,
        var tagList: List<String>? = null
)