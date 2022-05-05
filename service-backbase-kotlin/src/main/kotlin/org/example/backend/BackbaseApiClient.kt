package org.example.backend

import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification
import org.example.backend.config.BackbaseRequestSpec
import org.example.backend.config.BackendConfig
import org.example.backend.config.filter.AuthTokenFilter
import org.example.backend.config.filter.RequestIdFilter
import org.example.backend.model.request.ArticleCreateReqBody
import org.example.backend.model.request.UserLoginReqBody
import org.example.backend.model.request.UserRegisterReqBody
import org.example.backend.model.response.ArticleDetailsResponseBody
import org.example.backend.model.response.ArticlesListResponseBody
import org.example.backend.model.response.ErrorResBody
import org.example.backend.model.response.UserResBody
import org.example.backend.sampler.UserLoginSampler
import pl.net.testit.serum.api.filter.RequestHeaderFilter
import pl.net.testit.serum.api.filter.RequestQueryParamsFilter
import pl.net.testit.serum.api.response.ApiJsonResponse
import pl.net.testit.serum.api.response.ResponseStatusCode

class BackbaseApiClient {
    private val authTokenFilter: RequestHeaderFilter
    private val queryParamsFilter: RequestQueryParamsFilter
    private val requestSpec: RequestSpecification

    // api client helper method starts with _dash
    fun _authenticateUser(email: String?, password: String?) {
        val authOutput = loginUser(UserLoginSampler.fullInput(email, password)).assertOk()
        authTokenFilter.setValue("Token " + authOutput.user.token)
    }

    fun withQueryParams(queryParams: Map<String?, String?>?): BackbaseApiClient {
        queryParamsFilter.setQueryParamsForNextRequest(queryParams)
        return this
    }

    // api methods
    fun registerUser(reqBody: UserRegisterReqBody?): ApiJsonResponse<UserResBody, ErrorResBody> {
        val response = RestAssured.given(requestSpec).body(reqBody).post("/api/users")
        return ApiJsonResponse.from(response, UserResBody::class.java, ResponseStatusCode.OK, ErrorResBody::class.java)
    }

    fun loginUser(reqBody: UserLoginReqBody?): ApiJsonResponse<UserResBody, ErrorResBody> {
        val response = RestAssured.given(requestSpec).body(reqBody).post("/api/users/login")
        return ApiJsonResponse.from(response, UserResBody::class.java, ResponseStatusCode.OK, ErrorResBody::class.java)
    }

    val currentUser: ApiJsonResponse<UserResBody, ErrorResBody>
        get() {
            val response = RestAssured.given(requestSpec)["/api/user"]
            return ApiJsonResponse.from(response, UserResBody::class.java, ResponseStatusCode.OK, ErrorResBody::class.java)
        }

    fun createArticle(reqBody: ArticleCreateReqBody?): ApiJsonResponse<ArticleDetailsResponseBody, ErrorResBody> {
        val response = RestAssured.given(requestSpec).body(reqBody).post("/api/articles")
        return ApiJsonResponse.from(response, ArticleDetailsResponseBody::class.java, ResponseStatusCode.OK, ErrorResBody::class.java)
    }

    val articlesList: ApiJsonResponse<ArticlesListResponseBody, ErrorResBody>
        get() {
            val response = RestAssured.given(requestSpec)["/api/articles"]
            return ApiJsonResponse.from(response, ArticlesListResponseBody::class.java, ResponseStatusCode.OK, ErrorResBody::class.java)
        }

    init {
        authTokenFilter = AuthTokenFilter
        queryParamsFilter = RequestQueryParamsFilter()
        requestSpec = BackbaseRequestSpec.baseJsonRequestSpecBuilder()
                .setBaseUri(BackendConfig.API_URL)
                .addFilter(authTokenFilter)
                .addFilter(queryParamsFilter)
                .addFilter(RequestIdFilter())
                .build()
                .auth()
                .preemptive()
                .basic(BackendConfig.API_USERNAME, BackendConfig.API_PASSWORD)
    }
}