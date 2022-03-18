package org.example.backend.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseApiSuite {

  private static final Logger log = LoggerFactory.getLogger(BaseApiSuite.class);

  public void _given(String message, Object... args) {
    log.info("GIVEN: {}", String.format(message, args));
    log.info("---------------------------------------");
  }

  public void _when(String message, Object... args) {
    log.info("WHEN: {}", String.format(message, args));
    log.info("---------------------------------------");
  }

  public void _then(String message, Object... args) {
    log.info("THEN: {}", String.format(message, args));
    log.info("---------------------------------------");
  }

  public void _and(String message, Object... args) {
    log.info("AND: {}", String.format(message, args));
    log.info("---------------------------------------");
  }
}
