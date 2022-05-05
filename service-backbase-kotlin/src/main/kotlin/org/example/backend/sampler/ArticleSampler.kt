package org.example.backend.sampler

import org.example.backend.model.request.Article
import org.example.backend.model.request.ArticleCreateReqBody
import java.util.*

object ArticleSampler {
    fun minimalInput(): ArticleCreateReqBody {
        return ArticleCreateReqBody(
                article = Article(
                        title = "Title " + UUID.randomUUID().toString(),
                        description = "Description " + UUID.randomUUID().toString(),
                        body = "Body " + UUID.randomUUID().toString()
                )
        )

    }

    fun fullInput(): ArticleCreateReqBody {
        val input = minimalInput()
        input.article?.tagList = mutableListOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        return input
    }
}