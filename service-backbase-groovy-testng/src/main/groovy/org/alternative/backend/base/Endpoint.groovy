package org.alternative.backend.base

interface Endpoint<Input, Success, Error> {

  Response<Success, Error> execute(Input body)

}