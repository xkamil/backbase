package org.alternative.backend.base

import groovy.transform.Memoized

class Response<Success, Error> {

  final io.restassured.response.Response wrapped
  final int successCode
  private final Class<Success> successClass
  private final Class<Error> errorClass

  Response(io.restassured.response.Response wrapped, Class<Success> successClass, Class<Error> errorClass, int successCode) {
    this.wrapped = wrapped
    this.successClass = successClass
    this.errorClass = errorClass
    this.successCode = successCode
  }

  @Memoized
  int getCode() {
    wrapped.getStatusCode()
  }

  @Memoized
  Success parse() {
    (Success) wrapped.getBody().as(successClass)
  }

  @Memoized
  Error parseError() {
    (Error) wrapped.getBody().as(errorClass)
  }

  boolean isSuccess() {
    code == successCode
  }

}
