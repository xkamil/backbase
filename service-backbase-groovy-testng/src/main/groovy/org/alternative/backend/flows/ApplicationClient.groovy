package org.alternative.backend.flows

import org.alternative.backend.endpoints.article.ArticleService
import org.alternative.backend.endpoints.user.UserService

class ApplicationClient {

  private UserService userService
  private ArticleService articleService

  ApplicationClient(String token) {
    userService = new UserService(token)
    articleService = new ArticleService(token)
  }

  UserService user() { userService }

  ArticleService article() { articleService }
}
