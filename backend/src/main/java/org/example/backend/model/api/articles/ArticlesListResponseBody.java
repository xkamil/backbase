package org.example.backend.model.api.articles;

import java.util.List;
import org.example.backend.model.api.articles.common.ArticleView;


public class ArticlesListResponseBody {

  public List<ArticleView> articles;
  public Integer articlesCount;

}
