package org.example.backend.model.response;

import static com.google.common.truth.Truth.assertThat;

import io.restassured.response.Response;

public class ApiResponse<T> {

  private final Response response;
  private final Class<T> okResponseClass;

  public ApiResponse(Response response, Class<T> okResponseClass) {
    this.response = response;
    this.okResponseClass = okResponseClass;
  }

  public T ok() {
    assertThat(response.statusCode()).isLessThan(400);
    return response.as(okResponseClass);
  }

  public ErrorResBody shouldFail(int expectedStatusCode) {
    assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
    return response.as(ErrorResBody.class);
  }
}
