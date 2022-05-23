package org.example.backend.api.article

import groovy.transform.TypeChecked
import org.example.backend.BackbaseApiClient
import org.example.backend.model.request.ArticleCreateReqBody
import org.example.backend.model.response.UserResBody.User
import org.example.backend.sampler.ArticleSampler
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pl.net.testit.serum.reporting.BaseTestSuite

import java.util.stream.Stream

import static com.google.common.truth.Truth.assertThat
import static org.example.backend.config.StaticTestData.RegisteredUsers.USER_1
import static org.example.backend.extensions.GroovyAssertions.assertAll
import static org.junit.jupiter.params.provider.Arguments.of

@TypeChecked
@DisplayName('API: POST /api/articles')
class ArticleCreateTest extends BaseTestSuite {

	private static BackbaseApiClient backbaseApiClient
	private static User authenticatedUserDetails

	@BeforeAll
	static void beforeAll() {
		backbaseApiClient = new BackbaseApiClient().tap {
			_authenticateUser(USER_1.email, USER_1.password)
			authenticatedUserDetails = getCurrentUser().assertOk().user
		}
	}

	@DisplayName('create article')
	@ParameterizedTest(name = 'request body contains {0}')
	@MethodSource('provideValidArticleCreateInput')
	void createArticle(String description, ArticleCreateReqBody reqBody) {
		_given('valid create article request body')

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

	private static Stream<Arguments> provideValidArticleCreateInput() {
		return Stream.of(
				of('only required data', ArticleSampler.minimalInput()),
				of('full data', ArticleSampler.fullInput())
		)
	}

}
