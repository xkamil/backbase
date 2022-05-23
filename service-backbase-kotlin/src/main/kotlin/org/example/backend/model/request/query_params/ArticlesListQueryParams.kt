package org.example.backend.model.request.query_params

import pl.net.testit.serum.api.request.RequestQueryParams


class ArticlesListQueryParams(
        val tag: String? = null,
        val author: String? = null,
        val favorited: String? = null,
        val limit: Int? = null,
        val offset: Int? = null,
) : RequestQueryParams()