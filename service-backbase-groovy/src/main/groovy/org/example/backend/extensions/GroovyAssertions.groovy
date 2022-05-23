package org.example.backend.extensions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable

class GroovyAssertions {

  static assertAll(Closure... executable) {
    Assertions.assertAll(executable.flatten().collect { it as Executable })
  }

  static assertAll(List<Closure> executables) {
    Assertions.assertAll(executables.flatten().collect { it as Executable })
  }

}
