package org.example.backend.api.article;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.example.backend.BackbaseApiClient;
import org.example.backend.config.StaticTestData.RegisteredUsers;
import org.example.backend.model.request.query_params.ArticlesListQueryParams;
import org.example.backend.model.response.ArticleDetailsResponseBody;
import org.example.backend.model.response.ArticleDetailsResponseBody.Article;
import org.example.backend.sampler.ArticleSampler;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.net.testit.serum.reporting.BaseTestSuite;

@DisplayName("API: GET /api/articles")
class ArticlesListTest extends BaseTestSuite {

  private static final List<Article> existingArticles = new ArrayList<>();
  private static BackbaseApiClient backbaseApiClient;

  @BeforeAll
  static void beforeAll() {
    backbaseApiClient = new BackbaseApiClient();
    backbaseApiClient._authenticateUser(RegisteredUsers.USER_1.email, RegisteredUsers.USER_1.password);

    for (int i = 0; i < 3; i++) {
      val article = backbaseApiClient.createArticle(ArticleSampler.fullInput()).assertOk().article;
      existingArticles.add(article);
    }

  }

  @DisplayName("get articles list")
  @Test
  void getArticles() {
    _given("existing articles");
    Assumptions.assumeTrue(existingArticles.size() >= 3);

    _when("I get articles list");
    val response = backbaseApiClient.getArticlesList();

    _then("article details should be returned");
    val list = response.assertOk();
    assertAll(
        () -> assertThat(list.articles.size()).isAtLeast(existingArticles.size()),
        () -> assertThat(list.articlesCount).isAtLeast(existingArticles.size())
    );

  }

  @DisplayName("get articles list - filter by author")
  @Test
  void getArticlesFiltered() {
    _given("existing articles");
    Assumptions.assumeTrue(existingArticles.size() >= 1);
    ArticleDetailsResponseBody.Article article = existingArticles.get(0);

    _when("I get articles list filtered by author");
    val filter = new ArticlesListQueryParams().setAuthor(article.author.username).asMap();
    val response = backbaseApiClient.withQueryParams(filter).getArticlesList();

    _then("articles list for author %s should be returned", article.author);
    val list = response.assertOk();
    assertThat(list.articles).isNotEmpty();
    list.articles.forEach(a -> assertThat(a.author.username).isEqualTo(article.author.username));
  }

  @DisplayName("get articles list - with limit")
  @Test
  void getArticlesLimited() {
    _given("existing articles");
    Assumptions.assumeTrue(existingArticles.size() >= 3);
    val limit = 2;

    _when("I get articles list with limit");
    val filter = new ArticlesListQueryParams().setLimit(limit).asMap();
    val response = backbaseApiClient.withQueryParams(filter).getArticlesList();

    _then("articles list should contain %s articles", limit);
    val list = response.assertOk();
    assertThat(list.articles).hasSize(limit);
  }

}
