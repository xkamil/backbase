package org.example.backend.model.request.query_params

import pl.net.testit.serum.api.request.RequestQueryParams


class ArticlesListQueryParams : RequestQueryParams() {
    var tag: String? = null
    var author: String? = null
    var favorited: String? = null
    var limit: Int? = null
    var offset: Int? = null
}