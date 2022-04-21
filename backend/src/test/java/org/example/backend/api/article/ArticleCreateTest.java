package org.example.backend.api.article;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.serum.reporting.BaseTestSuite;
import java.util.List;
import java.util.stream.Stream;
import org.example.backend.ApiClient;
import org.example.backend.config.StaticTestData.RegisteredUsers;
import org.example.backend.api.user.UserResBody.User;
import org.example.backend.config.filter.RequestIdFilter;
import org.example.backend.model.api.articles.ArticleCreateReqBody;
import org.example.backend.sampler.ArticleSampler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.Request;

@DisplayName("article: create")
class ArticleCreateTest extends BaseTestSuite {

  private static ApiClient apiClient;
  private static User authenticatedUserDetails;

  @BeforeAll
  static void beforeAll() {
    apiClient = new ApiClient();
    apiClient.authenticateUser(RegisteredUsers.USER_1.email, RegisteredUsers.USER_1.password);
    authenticatedUserDetails = apiClient.getCurrentUser().assertOk().user;
  }

  @Test
  void test(){
    var articleCreateReqBody = ArticleSampler.minimalInput();
    var articleCreateReqBody2 = ArticleSampler.minimalInput();



    apiClient.createArticle(articleCreateReqBody).removeFilter(RequestIdFilter.class).execute();
    apiClient.createArticle(articleCreateReqBody2).execute();
  }

  @DisplayName("create article")
  @ParameterizedTest(name = "request body contains {0}")
  @MethodSource("provideValidArticleCreateInput")
  void createArticle(String description, ArticleCreateReqBody articleCreateReqBody) {
    _given("valid create article request body");

    _when("create article request is sent");
    var response = apiClient.createArticle(articleCreateReqBody)
        .removeHeader("request-id").execute();

    _then("article should be created");
    var responseBody = response.assertOk();
    var expectedTagList =
        articleCreateReqBody.article.tagList == null ? List.of() : articleCreateReqBody.article.tagList;

    assertAll(
        () -> assertThat(responseBody.article.title).isEqualTo(articleCreateReqBody.article.title),
        () -> assertThat(responseBody.article.description).isEqualTo(articleCreateReqBody.article.description),
        () -> assertThat(responseBody.article.body).isEqualTo(articleCreateReqBody.article.body),
        () -> assertThat(responseBody.article.tagList).isEqualTo(expectedTagList),
        () -> assertThat(responseBody.article.favorited).isFalse(),
        () -> assertThat(responseBody.article.favoritesCount).isEqualTo(0),

        () -> assertThat(responseBody.article.author).isNotNull(),
        () -> assertThat(responseBody.article.author.username).isEqualTo(authenticatedUserDetails.username),
        () -> assertThat(responseBody.article.author.bio).isEqualTo(authenticatedUserDetails.bio),
        () -> assertThat(responseBody.article.author.following).isFalse()
    );

  }

  private static Stream<Arguments> provideValidArticleCreateInput() {
    return Stream.of(
        Arguments.of("only required data", ArticleSampler.minimalInput()),
        Arguments.of("full data", ArticleSampler.fullInput())
    );
  }


}
