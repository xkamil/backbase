package org.alternative.backend.api.endpoints.article

import org.alternative.backend.endpoints.article.create.ArticleCreateRequestBody
import org.alternative.backend.endpoints.article.create.ArticleCreateRequestSampler
import org.alternative.backend.endpoints.user.UserResponseBody
import org.alternative.backend.flows.ApplicationClient
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import pl.net.testit.serum.reporting.BaseTestSuite

import static com.google.common.truth.Truth.assertThat
import static org.alternative.backend.base.config.StaticTestData.RegisteredUsers
import static org.alternative.backend.base.config.StaticTestData.User
import static org.alternative.backend.flows.TokenProvider.provideToken
import static org.example.backend.extensions.GroovyAssertions.assertAll

class ArticleCreateTest extends BaseTestSuite {

  ApplicationClient application
  UserResponseBody.User authenticatedUserDetails
  User testUser = RegisteredUsers.USER_1

  @BeforeClass
  void beforeClass() {
    application = new ApplicationClient(provideToken(testUser.email, testUser.password))
    authenticatedUserDetails = application.user().getCurrentUser().parse().user
  }

  @Test(description = 'create article', dataProvider = 'provideValidArticleCreateInput')
  void createArticle(String description, ArticleCreateRequestBody requestBody) {
    _given("valid create article request body with: $description")

    _when('create article request is sent')
    def response = application.article().create(requestBody)

    _then('article should be created')
    def responseBody = response.parse()
    def expectedTagList =
        requestBody.article.tagList == null ? [] : requestBody.article.tagList

    assertAll(
        { assert responseBody.article.title == requestBody.article.title },
        { assert responseBody.article.description == requestBody.article.description },
        { assert responseBody.article.body == requestBody.article.body },
        { assertThat(responseBody.article.tagList).containsExactlyElementsIn(expectedTagList).inOrder() },
        { assert !responseBody.article.favorited },
        { assert responseBody.article.favoritesCount == 0 },
        { assert responseBody.article.author != null },
        { assert responseBody.article.author.username == authenticatedUserDetails.username },
        { assert responseBody.article.author.bio == authenticatedUserDetails.bio },
        { assert !responseBody.article.author.following },
    )
  }

  @DataProvider
  Object[][] provideValidArticleCreateInput() {
    [
        ['only required data', new ArticleCreateRequestSampler().minimal()],
        ['full data', new ArticleCreateRequestSampler().full()],
    ] as Object[][]
  }

}
