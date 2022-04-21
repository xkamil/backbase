package org.example.backend.model.api.articles;

import com.example.serum.commons.json.JsonEntity;
import java.util.List;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(fluent = true)
public class ArticleCreateReqBody extends JsonEntity {

  public Article article;

  @Setter
  @Accessors(fluent = true)
  public static class Article {

    public String title;
    public String description;
    public String body;
    public List<String> tagList;
  }
}
