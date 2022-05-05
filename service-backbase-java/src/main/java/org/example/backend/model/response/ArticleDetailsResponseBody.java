package org.example.backend.model.response;

import java.util.List;
import pl.net.testit.serum.commons.json.JsonEntity;

public class ArticleDetailsResponseBody extends JsonEntity {

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
