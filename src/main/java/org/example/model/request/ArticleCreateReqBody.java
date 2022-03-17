package org.example.model.request;

import java.util.List;

public class ArticleCreateReqBody {

  public Article article;

  public static class Article {

    public String title;
    public String description;
    public String body;
    public List<String> tagList;
  }
}
