package org.alternative.backend.api.endpoints.user

import groovy.transform.TypeChecked
import io.qameta.allure.Issue
import org.alternative.backend.endpoints.user.UserService
import org.alternative.backend.endpoints.user.register.UserRegisterRequestBody
import org.alternative.backend.endpoints.user.register.UserRegisterRequestSampler
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import pl.net.testit.serum.reporting.BaseTestSuite

import static org.alternative.backend.base.config.StaticTestData.RegisteredUsers
import static org.example.backend.extensions.GroovyAssertions.assertAll

@TypeChecked
class UserRegisterTest extends BaseTestSuite {

  UserService userService

  @BeforeClass
  void beforeClass() {
    userService = new UserService()
  }

  @Test(description = 'register user')
  void registerUserTest() {
    _given('valid user register input')
    def requestBody = new UserRegisterRequestSampler().full()

    _when('user register request is sent')
    def response = userService.register().execute(requestBody)

    _then('response should be ok')
    def responseBody = response.parse()

    _and('user registration data should be returned')
    assertAll(
        { assert responseBody.user != null },
        { assert responseBody.user.email == requestBody.user.email },
        { assert responseBody.user.username == requestBody.user.username },
        { assert !responseBody.user.token.isEmpty() },
    )
  }

  @Test(description = 'register user with email already taken by other registered user - error expected')
  void registerUserEmailTakenTest() {
    _given('registered user email')
    def registeredUserEmail = RegisteredUsers.USER_1.email

    _and('valid user register input with email of already registered user')
    def requestBody = new UserRegisterRequestSampler().full().tap {
      user.email = registeredUserEmail
    }

    _when('user register request is sent')
    def response = userService.register().execute(requestBody)

    _then('response should return error')
    assert response.getCode() == 422
    assert response.parseError().errors == [email: 'is already taken.']
  }

  @Test(description = 'register user with username already taken by other registered user - error expected')
  void registerUserUsernameTest() {
    _given('registered user email')
    def registeredUserUsername = RegisteredUsers.USER_1.username

    _and('valid user register input with email of already registered user')
    def requestBody = new UserRegisterRequestSampler().full().tap {
      user.username = registeredUserUsername
    }

    _when('user register request is sent')
    def response = userService.register().execute(requestBody)

    _then('response should return error')
    assertAll(
        { assert response.getCode() == 422 },
        { assert response.parseError().errors == [username: 'is already taken.'] }
    )
  }

  @Issue('BUG_123')
  @Test(description = 'register user missing required data - error expected', dataProvider = 'provideInvalidRegisterUserData')
  void registerUserWithMissingDataTest(String missingField, UserRegisterRequestBody requestBody) {
    _given("user register data with missing $missingField")

    _when('user register request is sent')
    def response = userService.register().execute(requestBody)

    _then('response should return error')
    assertAll(
        { assert response.getCode() == 422 },
        { assert response.parseError().errors == [(missingField): "can't be blank"] }
    )
  }

  @DataProvider
  Object[][] provideInvalidRegisterUserData() {
    def sampler = new UserRegisterRequestSampler()
    [
        ['username', sampler.full().tap { user.username = null }],
        ['password', sampler.full().tap { user.password = null }],
        ['email', sampler.full().tap { user.email = null }]
    ] as Object[][]
  }

}
