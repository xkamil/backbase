package org.example.backend.sampler

import org.example.backend.model.request.ArticleCreateReqBody
import org.example.backend.model.request.ArticleCreateReqBodyArticle
import java.util.*

object ArticleCreateReqBodySampler {

  fun fullInput(modify: (ArticleCreateReqBody) -> Unit = {}): ArticleCreateReqBody {
    val reqBody = minimalInput();
    reqBody.article?.tagList = mutableListOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())

    modify.invoke(reqBody)
    return reqBody
  }

  fun minimalInput(modify: (ArticleCreateReqBody) -> Unit = {}): ArticleCreateReqBody {
    val reqBody = ArticleCreateReqBody(
      article = ArticleCreateReqBodyArticle(
        title = "Title " + UUID.randomUUID().toString(),
        description = "Description " + UUID.randomUUID().toString(),
        body = "Body " + UUID.randomUUID().toString(),
        tagList = null
      )
    )

    modify.invoke(reqBody)
    return reqBody;

  }

}