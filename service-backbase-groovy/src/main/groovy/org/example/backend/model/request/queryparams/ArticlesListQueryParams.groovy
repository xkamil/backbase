package org.example.backend.model.request.queryparams

import org.example.backend.extensions.GroovyRequestQueryParams

class ArticlesListQueryParams extends GroovyRequestQueryParams {

  String tag
  String author
  String favorited
  Integer limit
  Integer offset

}
