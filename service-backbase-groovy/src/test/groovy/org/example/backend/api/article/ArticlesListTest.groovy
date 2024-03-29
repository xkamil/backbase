package org.example.backend.api.article

import groovy.transform.TypeChecked
import org.example.backend.BackbaseApiClient
import org.example.backend.config.StaticTestData
import org.example.backend.config.StaticTestData.RegisteredUsers
import org.example.backend.model.request.queryparams.ArticlesListQueryParams
import org.example.backend.model.response.ArticleDetailsResponseBody.Article
import org.example.backend.sampler.ArticleSampler
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pl.net.testit.serum.reporting.BaseTestSuite

import static com.google.common.truth.Truth.assertThat
import static org.example.backend.extensions.GroovyAssertions.assertAll

@TypeChecked
@DisplayName('API: GET /api/articles')
class ArticlesListTest extends BaseTestSuite {

  private static final List<Article> existingArticles = new ArrayList<>()
  private static BackbaseApiClient backbaseApiClient
  static StaticTestData.User testUser = RegisteredUsers.USER_1


  @BeforeAll
  static void beforeAll() {
    backbaseApiClient = new BackbaseApiClient().tap {
      _authenticateUser(testUser.email, testUser.password)
      3.times {
        existingArticles << createArticle(ArticleSampler.fullInput()).assertOk().article
      }
    }
  }

  @DisplayName('get articles list')
  @Test
  void getArticles() {
    _given('existing articles')
    Assumptions.assumeTrue(existingArticles.size() >= 3)

    _when('I get articles list')
    def response = backbaseApiClient.getArticlesList()

    _then('article details should be returned')
    def list = response.assertOk()
    assertAll(
        { assertThat(list.articles.size()).isAtLeast(existingArticles.size()) },
        { assertThat(list.articlesCount).isAtLeast(existingArticles.size()) },
    )
  }

  @DisplayName('get articles list - filter by author')
  @Test
  void getArticlesFiltered() {
    _given('existing articles')
    Assumptions.assumeTrue(existingArticles.size() >= 1)
    Article article = existingArticles.get(0)

    _when('I get articles list filtered by author')
    def filter = new ArticlesListQueryParams().tap {
      author = article.author.username
    }.asMap()
    def response = backbaseApiClient.withQueryParams(filter).getArticlesList()

    _then("articles list for author $article.author should be returned")
    def list = response.assertOk()
    assertThat(list.articles).isNotEmpty()
    assertAll(
        list.articles.collect { l ->
          { assertThat(l.author.username).isEqualTo(article.author.username) } as Closure
        }
    )
  }

  @DisplayName('get articles list - with limit')
  @Test
  void getArticlesLimited() {
    _given('existing articles')
    Assumptions.assumeTrue(existingArticles.size() >= 3)
    def limit = 2

    _when('I get articles list with limit')
    def filter = new ArticlesListQueryParams().tap { it.limit = limit }.asMap()
    def response = backbaseApiClient.withQueryParams(filter).getArticlesList()

    _then("articles list should contain $limit articles")
    def list = response.assertOk()
    assertThat(list.articles).hasSize(limit)
  }

}
