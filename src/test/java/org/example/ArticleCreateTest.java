package org.example;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;
import org.example.config.StaticTestData.RegisteredUsers;
import org.example.model.request.ArticleCreateReqBody;
import org.example.model.response.UserResBody.User;
import org.example.sampler.ArticleSampler;
import org.example.test.BaseApiSuite;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("article: create")
public class ArticleCreateTest extends BaseApiSuite {

  private static BackbaseApiClient apiClient;
  private static User authenticatedUserDetails;

  @BeforeAll
  public static void beforeAll() {
    apiClient = new BackbaseApiClient();
     apiClient.authenticateUser(RegisteredUsers.USER1_EMAIL, RegisteredUsers.USER2_PASSWORD);
     authenticatedUserDetails = apiClient.getCurrentUser().ok().user;
  }

  @DisplayName("create article")
  @ParameterizedTest(name = "request body contains {0}")
  @MethodSource("provideValidArticleCreateInput")
  public void createArticle(String description, ArticleCreateReqBody articleCreateReqBody) {
    _given("valid create article request body");

    _when("create article request is sent");
    var response = apiClient.createArticle(articleCreateReqBody);

    _then("article should be created");
    var responseBody = response.ok();

    assertAll(
        () -> assertThat(responseBody.article.title).isEqualTo(articleCreateReqBody.article.title),
        () -> assertThat(responseBody.article.description).isEqualTo(articleCreateReqBody.article.description),
        () -> assertThat(responseBody.article.body).isEqualTo(articleCreateReqBody.article.body),
        () -> assertThat(responseBody.article.tagList).isEqualTo(articleCreateReqBody.article.tagList),
        () -> assertThat(responseBody.article.favorited).isFalse(),
        () -> assertThat(responseBody.article.favoritesCount).isEqualTo(0),

        () -> assertThat(responseBody.article.author).isNotNull(),
        () -> assertThat(responseBody.article.author.username).isEqualTo(authenticatedUserDetails.username),
        () -> assertThat(responseBody.article.author.bio).isEqualTo(authenticatedUserDetails.bio),
        () -> assertThat(responseBody.article.author.image).isEqualTo(authenticatedUserDetails.image),
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
