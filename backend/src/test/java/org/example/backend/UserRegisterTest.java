package org.example.backend;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import java.util.stream.Stream;
import org.example.backend.config.StaticTestData.RegisteredUsers;
import org.example.backend.model.request.UserRegisterReqBody;
import org.example.backend.sampler.UserRegisterSampler;
import org.example.backend.test.BaseApiSuite;
import org.example.backend.test.Issue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("user: register")
class UserRegisterTest extends BaseApiSuite {

  private static ApiClient apiClient;

  @BeforeAll
  static void beforeAll() {
    apiClient = new ApiClient();
  }

  @Test
  @Tag("SMOKE")
  @DisplayName("register user")
  void registerUser() {
    _given("valid user register input");
    var userRegisterReqBody = UserRegisterSampler.fullInput();

    _when("user register request is sent");
    var response = apiClient.registerUser(userRegisterReqBody);

    _then("response should be ok");
    var responseBody = response.ok();

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
    var response = apiClient.registerUser(userRegisterReqBody);

    _then("response should return error");
    var responseBody = response.shouldFail(422);
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
    var response = apiClient.registerUser(userRegisterReqBody);

    _then("response should return error");
    var responseBody = response.shouldFail(422);
    assertThat(responseBody.errors).isEqualTo(Map.of("username", "is already taken."));
  }

  @Issue("ISSUE_1")
  @DisplayName("register user missing required data - error expected")
  @ParameterizedTest(name = "missing {0}")
  @MethodSource("provideInvalidRegisterUserData")
  void registerUserWithMissingData(String missingField, UserRegisterReqBody userRegisterReqBody) {
    _given("user register data with missing " + missingField);

    _when("user register request is sent");
    var response = apiClient.registerUser(userRegisterReqBody);

    _then("response should return error");
    var responseBody = response.shouldFail(422);
    assertThat(responseBody.errors).isEqualTo(Map.of(missingField, "can't be blank"));
  }

  private static Stream<Arguments> provideInvalidRegisterUserData() {
    return Stream.of(
        Arguments.of("username", UserRegisterSampler.fullInput(m -> m.user.username = null)),
        Arguments.of("password", UserRegisterSampler.fullInput(m -> m.user.password = null)),
        Arguments.of("email", UserRegisterSampler.fullInput(m -> m.user.email = null))
    );
  }

}
