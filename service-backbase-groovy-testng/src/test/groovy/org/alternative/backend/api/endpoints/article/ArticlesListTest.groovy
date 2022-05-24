package org.alternative.backend.api.endpoints.article

import org.alternative.backend.endpoints.article.ArticleRequests
import org.alternative.backend.endpoints.article.create.ArticleCreateRequestSampler
import org.alternative.backend.endpoints.article.create.ArticleCreateResponseBody.Article
import org.alternative.backend.endpoints.article.list.ArticlesListQueryParams
import org.junit.jupiter.api.Assumptions
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import pl.net.testit.serum.reporting.BaseTestSuite

import static com.google.common.truth.Truth.assertThat
import static org.alternative.backend.base.config.StaticTestData.RegisteredUsers
import static org.alternative.backend.base.config.StaticTestData.User
import static org.alternative.backend.flows.TokenProvider.provideToken
import static org.example.backend.extensions.GroovyAssertions.assertAll

class ArticlesListTest extends BaseTestSuite {

  ArticleRequests articleService
  List<Article> existingArticles = new ArrayList<>()
  User testUser = RegisteredUsers.USER_1

  @BeforeClass
  void beforeClass() {
    articleService = new ArticleRequests(provideToken(testUser.email, testUser.password))
    3.times {
      existingArticles << articleService.create(new ArticleCreateRequestSampler().full()).parse().article
    }
  }

  @Test(description = 'get articles list')
  void getArticlesTest() {
    _given('existing articles')
    Assumptions.assumeTrue(existingArticles.size() >= 3)

    _when('I get articles list')
    def response = articleService.list()

    _then('article details should be returned')
    def list = response.parse()
    assertAll(
        { assertThat(list.articles.size()).isAtLeast(existingArticles.size()) },
        { assertThat(list.articlesCount).isAtLeast(existingArticles.size()) },
    )
  }

  @Test(description = 'get articles list - filter by author')
  void getArticlesFilteredTest() {
    _given('existing articles')
    Assumptions.assumeTrue(!existingArticles.isEmpty())
    Article article = existingArticles.first()

    _when('I get articles list filtered by author')
    def filter = new ArticlesListQueryParams().tap {
      author = article.author.username
    }
    def response = articleService.list(filter)

    _then("articles list for author $article.author should be returned")
    def list = response.parse()
    assertThat(list.articles).isNotEmpty()
    assertAll(
        list.articles.collect { l ->
          { assertThat(l.author.username).isEqualTo(article.author.username) } as Closure
        }
    )
  }

  @Test(description = 'get articles list - with limit')
  void getArticlesLimitedTest() {
    _given('existing articles')
    Assumptions.assumeTrue(existingArticles.size() >= 3)
    def limit = 2

    _when('I get articles list with limit')
    def filter = new ArticlesListQueryParams().tap { it.limit = limit }
    def response = articleService.list(filter)

    _then("articles list should contain $limit articles")
    def list = response.parse()
    assertThat(list.articles).hasSize(limit)
  }

}
