package org.example.frontend;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.example.frontend.test.BaseWebSuite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

@DisplayName("user: login")
class UserLoginTest extends BaseWebSuite {

  @Test
  void createArticle() {


    open("/");
    $(By.name("user.name")).setValue("johny");
    $("#submit").click();

  }

}
