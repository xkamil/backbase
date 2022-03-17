package org.example.sampler;

import java.util.List;
import java.util.UUID;
import org.example.model.request.ArticleCreateReqBody;
import org.example.model.request.ArticleCreateReqBody.Article;

public class ArticleSampler {

  public static ArticleCreateReqBody minimalInput() {
    var reqBody = new ArticleCreateReqBody();
    reqBody.article = new Article();
    reqBody.article.title = "Title " + UUID.randomUUID().toString();
    reqBody.article.description = "Description " + UUID.randomUUID().toString();
    reqBody.article.body = "Body " + UUID.randomUUID().toString();
    return reqBody;
  }

  public static ArticleCreateReqBody fullInput() {
    ArticleCreateReqBody reqBody = minimalInput();
    reqBody.article.tagList = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    return reqBody;
  }
}
