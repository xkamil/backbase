package org.example.backend.api.article;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.serum.reporting.BaseTestSuite;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.example.backend.ApiClient;
import org.example.backend.config.StaticTestData.RegisteredUsers;
import org.example.backend.model.api.articles.ArticleDetailsResponseBody.Article;
import org.example.backend.model.api.articles.ArticlesListQueryParams;
import org.example.backend.sampler.ArticleSampler;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("article: get list")
class ArticlesListTest extends BaseTestSuite {

  private static ApiClient apiClient;

  private static final List<Article> existingArticles = new ArrayList<>();

  @BeforeAll
  static void beforeAll() {
    apiClient = new ApiClient();
    apiClient.authenticateUser(RegisteredUsers.USER_1.email, RegisteredUsers.USER_1.password);

    for (int i = 0; i < 3; i++) {
      val article = apiClient.createArticle(ArticleSampler.fullInput()).execute().assertOk().article;
      existingArticles.add(article);
    }

  }

  @DisplayName("get article list")
  @Test
  void getArticles() {
    _given("existing articles");
    Assumptions.assumeTrue(existingArticles.size() >= 3);

    _when("I get articles list");
    val response = apiClient.getArticlesList().execute();

    _then("article details should be returned");
    val list = response.assertOk();
    assertAll(
        () -> assertThat(list.articles.size()).isAtLeast(existingArticles.size()),
        () -> assertThat(list.articlesCount).isAtLeast(existingArticles.size())
    );

  }

  @DisplayName("get article list - filter by author")
  @Test
  void getArticlesFiltered() {
    _given("existing articles");
    Assumptions.assumeTrue(existingArticles.size() >= 1);
    val article = existingArticles.get(0);

    _when("I get articles list filtered by author");
    val filter = new ArticlesListQueryParams().setAuthor(article.author.username);
    val response = apiClient.getArticlesList().queryParams(filter).execute();

    _then("articles list for author %s should be returned", article.author);
    val list = response.assertOk();
    assertThat(list.articles).isNotEmpty();
    list.articles.forEach(a -> assertThat(a.author.username).isEqualTo(article.author.username));
  }

  @DisplayName("get article list - with limit")
  @Test
  void getArticlesLimited() {
    _given("existing articles");
    Assumptions.assumeTrue(existingArticles.size() >= 3);
    val limit = 2;

    _when("I get articles list with limit");
    val filter = new ArticlesListQueryParams().setLimit(limit);
    val response = apiClient.getArticlesList().queryParams(filter).execute();

    _then("articles list should contain %s articles", limit);
    val list = response.assertOk();
    assertThat(list.articles).hasSize(limit);
  }

}
