package org.example.backend.api.article

import org.example.backend.BackbaseApiClient
import org.example.backend.model.request.ArticleCreateReqBody
import org.example.backend.model.response.UserResBody.User
import org.example.backend.sampler.ArticleSampler
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import pl.net.testit.serum.reporting.BaseTestSuite

import static com.google.common.truth.Truth.assertThat
import static org.example.backend.config.StaticTestData.RegisteredUsers.USER_1
import static org.example.backend.extensions.GroovyAssertions.assertAll

class ArticleCreateTest extends BaseTestSuite {

  private static BackbaseApiClient backbaseApiClient
  private static User authenticatedUserDetails

  @BeforeClass
  static void beforeAll() {
    backbaseApiClient = new BackbaseApiClient().tap {
      _authenticateUser(USER_1.email, USER_1.password)
      authenticatedUserDetails = getCurrentUser().assertOk().user
    }
  }

  @Test(description = 'create article', dataProvider = 'provideValidArticleCreateInput')
  void createArticle(String description, ArticleCreateReqBody reqBody) {
    _given("valid create article request body with $description")

    _when('create article request is sent')
    def response = backbaseApiClient.createArticle(reqBody)

    _then('article should be created')
    def responseBody = response.assertOk()
    def expectedTagList =
        reqBody.article.tagList == null ? [] : reqBody.article.tagList

    assertAll(
        { assert responseBody.article.title == reqBody.article.title },
        { assert responseBody.article.description == reqBody.article.description },
        { assert responseBody.article.body == reqBody.article.body },
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
        ['only required data', ArticleSampler.minimalInput()],
        ['full data', ArticleSampler.fullInput()],
    ] as Object[][]
  }

}
