package org.example.frontend.test;

import org.example.frontend.config.FrontendConfig;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseWebSuite {

  private static final Logger log = LoggerFactory.getLogger(BaseWebSuite.class);

  @BeforeAll
  static void beforeAll() {
    FrontendConfig.initSelenideConfiguration();
  }

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
