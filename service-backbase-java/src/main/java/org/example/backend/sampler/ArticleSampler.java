package org.example.backend.sampler;

import java.util.List;
import java.util.UUID;
import org.example.backend.model.request.ArticleCreateReqBody;
import org.example.backend.model.request.ArticleCreateReqBody.Article;

public class ArticleSampler {

  public static ArticleCreateReqBody minimalInput() {
    var article = new Article()
        .title("Title " + UUID.randomUUID().toString())
        .description("Description " + UUID.randomUUID().toString())
        .body("Body " + UUID.randomUUID().toString());

    return new ArticleCreateReqBody().article(article);
  }

  public static ArticleCreateReqBody fullInput() {
    var input = minimalInput();
    input.article.tagList(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
    return input;
  }
}
