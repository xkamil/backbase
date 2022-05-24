package org.alternative.backend.base

/**
 * Safe extracting data from responses.
 * */
class EnsureResponse {

  /**
   * Ensures response was succeed or not by expected status code
   * and extracts the data via invoke Closure.
   * Extracted data validated on null.
   * @param expectSuccess optional, true by-default. Set to false if error expected.
   * */
  static extract(Response response, boolean expectSuccess = true, Closure getResponseData) {
    assert response.isSuccess() == expectSuccess
    def result = getResponseData()
    assert result
    result
  }
}
