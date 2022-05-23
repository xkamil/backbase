package org.example.backend.config.filter

import pl.net.testit.serum.api.filter.RequestHeaderFilter
import java.util.*

class RequestIdFilter : RequestHeaderFilter("Request-Id") {
  init {
    this.setValue { "|" + UUID.randomUUID() + "." }
  }
}