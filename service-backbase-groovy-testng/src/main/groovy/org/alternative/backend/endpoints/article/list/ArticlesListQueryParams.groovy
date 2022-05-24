package org.alternative.backend.endpoints.article.list

import org.alternative.backend.base.GroovyRequestQueryParams

class ArticlesListQueryParams extends GroovyRequestQueryParams {

  String tag
  String author
  String favorited
  Integer limit
  Integer offset

}
