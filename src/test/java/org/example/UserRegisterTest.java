package org.example;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import java.util.stream.Stream;
import org.example.config.StaticTestData.RegisteredUsers;
import org.example.model.request.UserRegisterReqBody;
import org.example.sampler.UserRegisterSampler;
import org.example.test.BaseApiSuite;
import org.example.test.Issue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("user: register")
public class UserRegisterTest extends BaseApiSuite {

  private static BackbaseApiClient apiClient;

  @BeforeAll
  public static void beforeAll() {
    apiClient = new BackbaseApiClient();
  }

  @Test
  @Tag("SMOKE")
  @DisplayName("register user")
  public void registerUser() {
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
  public void registerUserEmailTaken() {
    _given("registered user email");
    var registeredUserEmail = RegisteredUsers.USER1_EMAIL;

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
  public void registerUserUsername() {
    _given("registered user email");
    var registeredUserUsername = RegisteredUsers.USER1_USERNAME;

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
  public void registerUserWithMissingData(String missingField, UserRegisterReqBody userRegisterReqBody) {
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
