package org.example.frontend.config;

import com.codeborne.selenide.Configuration;

public class FrontendConfig {

  public static final String API_URL = "https://qa-task.backbasecloud.com";
  public static final String API_USERNAME = "candidatex";
  public static final String API_PASSWORD = "qa-is-cool";


  private static boolean selenideInitiated = false;

  public static void initSelenideConfiguration() {
    if (!selenideInitiated) {
      Configuration.baseUrl = "https://candidatex:qa-is-cool@qa-task.backbasecloud.com";
      Configuration.headless = false; //TODO read from env variable
      selenideInitiated = true;
    }

  }

}
