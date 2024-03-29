package org.alternative.backend.base

import groovy.contracts.Requires

class DataGenerator {

  @Requires({ length in 0..36 })
  static String generateUuidString(int length = 36) {
    UUID.randomUUID().toString()[0..(length - 1)]
  }

  static String generateUsername() {
    generateUuidString().replace('-', '')
  }

  static String generateEmail() {
    "${generateUuidString()}@example.com"
  }

  static String generatePassword() {
    generateUuidString(26).replace('-', '')
  }


}
