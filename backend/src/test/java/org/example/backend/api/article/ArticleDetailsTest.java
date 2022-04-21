package org.example.backend.api.article;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.serum.api.response.ResponseStatusCode;
import com.example.serum.reporting.BaseTestSuite;
import java.util.UUID;
import org.example.backend.ApiClient;
import org.example.backend.config.StaticTestData.RegisteredUsers;
import org.example.backend.sampler.ArticleSampler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("article: get details")
class ArticleDetailsTest extends BaseTestSuite {

  private static ApiClient apiClient;

  @BeforeAll
  static void beforeAll() {
    apiClient = new ApiClient();
    apiClient.authenticateUser(RegisteredUsers.USER_1.email, RegisteredUsers.USER_1.password);
  }

  @DisplayName("get article details")
  @Test
  void getArticle() {
    _given("existing article");
    var article = apiClient.createArticle(ArticleSampler.fullInput()).execute().assertOk().article;

    _when("I get article details");
    var response = apiClient.getArticleDetails().sendRequest(article.slug);

    _then("article details should be returned");
    var articleDetails = response.assertOk();

    assertAll(
        () -> assertThat(articleDetails.article.slug).isEqualTo(article.slug),
        () -> assertThat(articleDetails.article.body).isEqualTo(article.body)
    );
  }

  @DisplayName("get non existing article details - error expected")
  @Test
  void getNotExistingArticle() {
    _given("not existing article slug");
    var slug = UUID.randomUUID().toString();

    _when("I get article details");
    var response = apiClient.getArticleDetails().sendRequest(slug);

    _then("error should be returned");
    var error = response.assertError(ResponseStatusCode.NOT_FOUND);
  }

}
