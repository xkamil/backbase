package api.article

import com.google.common.truth.Truth
import org.example.backend.BackbaseApiClient
import org.example.backend.config.StaticTestData
import org.example.backend.model.request.ArticleCreateReqBody
import org.example.backend.model.response.ArticleDetailsResponseBody
import org.example.backend.model.response.UserResBodyUser
import org.example.backend.sampler.ArticleCreateReqBodySampler
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource
import pl.net.testit.serum.reporting.BaseTestSuite

@DisplayName("API: POST /api/articles")
class ArticleCreateTest : BaseTestSuite() {

  companion object {
    var backbaseApiClient: BackbaseApiClient = BackbaseApiClient()
    var authenticatedUserDetails: UserResBodyUser? = null

    @BeforeAll
    @JvmStatic
    fun beforeAll() {
      println("dupa")
      backbaseApiClient._authenticateUser(StaticTestData.RegisteredUsers.USER_1.email, StaticTestData.RegisteredUsers.USER_1.password)
      println("dupa1")
      authenticatedUserDetails = backbaseApiClient.getUserDetails().assertOk().user
    }

    @JvmStatic
    fun provideValidArticleCreateInput() = listOf(
      of("only required data", ArticleCreateReqBodySampler.minimalInput()),
      of("full data", ArticleCreateReqBodySampler.fullInput())
    )

  }

  @DisplayName("create article")
  @ParameterizedTest(name = "request body contains {0}")
  @MethodSource("provideValidArticleCreateInput")
  fun createArticle(description: String, articleCreateReqBody: ArticleCreateReqBody) {
    _given("valid create article request body")

    _when("create article request is sent")
    val response = backbaseApiClient.createArticle(articleCreateReqBody)

    _then("article should be created")
    val responseBody: ArticleDetailsResponseBody = response.assertOk()
    val expectedTagList = if (articleCreateReqBody.article?.tagList == null) listOf<Any>() else articleCreateReqBody.article?.tagList
    Assertions.assertAll(
      Executable { Truth.assertThat(responseBody.article?.title).isEqualTo(articleCreateReqBody.article!!.title) },
      Executable { Truth.assertThat(responseBody.article?.description).isEqualTo(articleCreateReqBody.article!!.description) },
      Executable { Truth.assertThat(responseBody.article?.body).isEqualTo(articleCreateReqBody.article!!.body) },
      Executable { Truth.assertThat(responseBody.article?.tagList).isEqualTo(expectedTagList) },
      Executable { Truth.assertThat(responseBody.article?.favorited).isFalse() },
      Executable { Truth.assertThat(responseBody.article?.favoritesCount).isEqualTo(0) },
      Executable { Truth.assertThat(responseBody.article?.author).isNotNull() },
      Executable { Truth.assertThat(responseBody.article?.author!!.username).isEqualTo(authenticatedUserDetails?.username) },
      Executable { Truth.assertThat(responseBody.article?.author!!.bio).isEqualTo(authenticatedUserDetails?.bio) },
      Executable { Truth.assertThat(responseBody.article?.author!!.following).isFalse() }
    )
  }


}