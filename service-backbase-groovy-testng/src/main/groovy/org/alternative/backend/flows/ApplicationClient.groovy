package org.alternative.backend.flows

import org.alternative.backend.endpoints.article.ArticleRequests
import org.alternative.backend.endpoints.user.UserRequests

class ApplicationClient {

  private UserRequests userService
  private ArticleRequests articleService

  ApplicationClient(String token) {
    userService = new UserRequests(token)
    articleService = new ArticleRequests(token)
  }

  UserRequests user() { userService }

  ArticleRequests article() { articleService }
}
