package org.example.model.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleResBody {

  public Article article;

  public static class Article {

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

  }

  public static class Author {

    public String username;
    public String bio;
    public String image;
    public Boolean following;
  }
}
