package org.example.backend.model.response

import pl.net.testit.serum.commons.json.JsonEntity

class ErrorResBody : JsonEntity() {
    var errors: Map<String, String>? = null
}