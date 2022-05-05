package org.example.backend.model.response

import pl.net.testit.serum.commons.json.JsonEntity

class UserResBody : JsonEntity() {
    var user: User? = null

    class User {
        var email: String? = null
        var token: String? = null
        var username: String? = null
        var bio: String? = null
        var image: String? = null
    }
}