package org.example.backend.config.filter

import pl.net.testit.serum.api.filter.RequestHeaderFilter

object AuthTokenFilter : RequestHeaderFilter("jwtauthorization") {
}