package org.example.backend.api.article;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.List;
import java.util.stream.Stream;
import lombok.val;
import org.example.backend.BackbaseApiClient;
import org.example.backend.config.StaticTestData.RegisteredUsers;
import org.example.backend.model.request.ArticleCreateReqBody;
import org.example.backend.model.response.UserResBody.User;
import org.example.backend.sampler.ArticleSampler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.net.testit.serum.reporting.BaseTestSuite;

@DisplayName("API: POST /api/articles")
class ArticleCreateTest extends BaseTestSuite {

  private static BackbaseApiClient backbaseApiClient;
  private static User authenticatedUserDetails;

  @BeforeAll
  static void beforeAll() {
    backbaseApiClient = new BackbaseApiClient();
    backbaseApiClient._authenticateUser(RegisteredUsers.USER_1.email, RegisteredUsers.USER_1.password);
    authenticatedUserDetails = backbaseApiClient.getCurrentUser().assertOk().user;
  }

  @DisplayName("create article")
  @ParameterizedTest(name = "request body contains {0}")
  @MethodSource("provideValidArticleCreateInput")
  void createArticle(String description, ArticleCreateReqBody articleCreateReqBody) {
    _given("valid create article request body");

    _when("create article request is sent");
    val response = backbaseApiClient.createArticle(articleCreateReqBody);

    _then("article should be created");
    val responseBody = response.assertOk();
    val expectedTagList =
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
        of("only required data", ArticleSampler.minimalInput()),
        of("full data", ArticleSampler.fullInput())
    );
  }


}
