package org.example.backend.model.response;

import java.util.List;


public class ArticlesListResponseBody {

  public List<ArticleView> articles;
  public Integer articlesCount;

  public static class ArticleView {

    public String slug;
    public String title;
    public String description;
    public String body;
    public List<String> tagList;
    public String createdAt;
    public String updatedAt;
    public Boolean favorited;
    public Integer favoritesCount;
    public Author author;


    public static class Author {

      public String username;
      public String bio;
      public String image;
      public Boolean following;
    }
  }
}
