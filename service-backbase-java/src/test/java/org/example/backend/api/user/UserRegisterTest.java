package org.example.backend.api.user;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.params.provider.Arguments.of;

import io.qameta.allure.Issue;
import java.util.Map;
import java.util.stream.Stream;
import org.example.backend.BackbaseApiClient;
import org.example.backend.config.StaticTestData.RegisteredUsers;
import org.example.backend.model.request.UserRegisterReqBody;
import org.example.backend.sampler.UserRegisterSampler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.net.testit.serum.reporting.BaseTestSuite;

@DisplayName("API: POST /api/users")
class UserRegisterTest extends BaseTestSuite {

  private static BackbaseApiClient backbaseApiClient;

  @BeforeAll
  static void beforeAll() {
    backbaseApiClient = new BackbaseApiClient();
  }

  @Test
  @DisplayName("register user")
  void registerUser() {
    _given("valid user register input");
    var userRegisterReqBody = UserRegisterSampler.fullInput();

    _when("user register request is sent");
    var response = backbaseApiClient.registerUser(userRegisterReqBody);

    _then("response should be ok");
    var responseBody = response.assertOk();

    _and("user registration data should be returned");
    assertAll(
        () -> assertThat(responseBody.user).isNotNull(),
        () -> assertThat(responseBody.user.email).isEqualTo(userRegisterReqBody.user.email),
        () -> assertThat(responseBody.user.username).isEqualTo(userRegisterReqBody.user.username),
        () -> assertThat(responseBody.user.token).isNotEmpty()
    );
  }

  @Test
  @DisplayName("register user with email already taken by other registered user - error expected")
  void registerUserEmailTaken() {
    _given("registered user email");
    var registeredUserEmail = RegisteredUsers.USER_1.email;

    _and("valid user register input with email of already registered user");
    var userRegisterReqBody = UserRegisterSampler.fullInput();
    userRegisterReqBody.user.email = registeredUserEmail;

    _when("user register request is sent");
    var response = backbaseApiClient.registerUser(userRegisterReqBody);

    _then("response should return error");
    var responseBody = response.assertError(422);
    assertThat(responseBody.errors).isEqualTo(Map.of("email", "is already taken."));
  }

  @Test
  @DisplayName("register user with username already taken by other registered user - error expected")
  void registerUserUsername() {
    _given("registered user email");
    var registeredUserUsername = RegisteredUsers.USER_1.username;

    _and("valid user register input with email of already registered user");
    var userRegisterReqBody = UserRegisterSampler.fullInput();
    userRegisterReqBody.user.username = registeredUserUsername;

    _when("user register request is sent");
    var response = backbaseApiClient.registerUser(userRegisterReqBody);

    _then("response should return error");
    var responseBody = response.assertError(422);
    assertThat(responseBody.errors).isEqualTo(Map.of("username", "is already taken."));
  }

  @Issue("BUG_123")
  @DisplayName("register user missing required data - error expected")
  @ParameterizedTest(name = "missing {0}")
  @MethodSource("provideInvalidRegisterUserData")
  void registerUserWithMissingData(String missingField, UserRegisterReqBody userRegisterReqBody) {
    _given("user register data with missing " + missingField);

    _when("user register request is sent");
    var response = backbaseApiClient.registerUser(userRegisterReqBody);

    _then("response should return error");
    var responseBody = response.assertError(422);
    assertThat(responseBody.errors).isEqualTo(Map.of(missingField, "can't be blank"));
  }

  private static Stream<Arguments> provideInvalidRegisterUserData() {
    return Stream.of(
        of("username", UserRegisterSampler.fullInput(m -> m.username = null)),
        of("password", UserRegisterSampler.fullInput(m -> m.password = null)),
        of("email", UserRegisterSampler.fullInput(m -> m.email = null))
    );
  }

}
