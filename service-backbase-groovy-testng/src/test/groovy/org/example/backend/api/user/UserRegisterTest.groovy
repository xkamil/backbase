package org.example.backend.api.user

import io.qameta.allure.Issue
import org.example.backend.BackbaseApiClient
import org.example.backend.config.StaticTestData.RegisteredUsers
import org.example.backend.model.request.UserRegisterReqBody
import org.example.backend.sampler.UserRegisterSampler
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import pl.net.testit.serum.reporting.BaseTestSuite

import static org.example.backend.extensions.GroovyAssertions.assertAll

class UserRegisterTest extends BaseTestSuite {

  private static BackbaseApiClient backbaseApiClient

  @BeforeClass
  static void beforeAll() {
    backbaseApiClient = new BackbaseApiClient()
  }

  @Test(description = 'register user')
  void registerUser() {
    _given('valid user register input')
    def userRegisterReqBody = UserRegisterSampler.fullInput()

    _when('user register request is sent')
    def response = backbaseApiClient.registerUser(userRegisterReqBody)

    _then('response should be ok')
    def responseBody = response.assertOk()

    _and('user registration data should be returned')
    assertAll(
        { assert responseBody.user != null },
        { assert responseBody.user.email == userRegisterReqBody.user.email },
        { assert responseBody.user.username == userRegisterReqBody.user.username },
        { assert !responseBody.user.token.isEmpty() },
    )
  }

  @Test(description = 'register user with email already taken by other registered user - error expected')
  void registerUserEmailTaken() {
    _given('registered user email')
    def registeredUserEmail = RegisteredUsers.USER_1.email

    _and('valid user register input with email of already registered user')
    def userRegisterReqBody = UserRegisterSampler.fullInput()
    userRegisterReqBody.user.email = registeredUserEmail

    _when('user register request is sent')
    def response = backbaseApiClient.registerUser(userRegisterReqBody)

    _then('response should return error')
    def responseBody = response.assertError(422)
    assert responseBody.errors == [email: 'is already taken.']
  }

  @Test(description = 'register user with username already taken by other registered user - error expected')
  void registerUserUsername() {
    _given('registered user email')
    def registeredUserUsername = RegisteredUsers.USER_1.username

    _and('valid user register input with email of already registered user')
    def userRegisterReqBody = UserRegisterSampler.fullInput()
    userRegisterReqBody.user.username = registeredUserUsername

    _when('user register request is sent')
    def response = backbaseApiClient.registerUser(userRegisterReqBody)

    _then('response should return error')
    def responseBody = response.assertError(422)
    assert responseBody.errors == [username: 'is already taken.']
  }

  @Issue('BUG_123')
  @Test(description = 'register user missing required data - error expected', dataProvider = 'provideInvalidRegisterUserData')
  void registerUserWithMissingData(String missingField, UserRegisterReqBody userRegisterReqBody) {
    _given("user register data with missing $missingField")

    _when('user register request is sent')
    def response = backbaseApiClient.registerUser(userRegisterReqBody)

    _then('response should return error')
    def responseBody = response.assertError(422)
    assert responseBody.errors == [(missingField): "can't be blank"]
  }

  @DataProvider
  Object[][] provideInvalidRegisterUserData() {
    [
        ['username', UserRegisterSampler.fullInput().tap { user.username = null }],
        ['password', UserRegisterSampler.fullInput().tap { user.password = null }],
        ['email', UserRegisterSampler.fullInput().tap { user.email = null }]
    ] as Object[][]
  }

}
