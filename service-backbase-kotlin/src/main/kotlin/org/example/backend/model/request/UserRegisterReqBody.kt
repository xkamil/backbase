package org.example.backend.model.request

import pl.net.testit.serum.commons.json.JsonEntity


class UserRegisterReqBody : JsonEntity() {
    var user: User? = null


}

class UserRegisterReqBodyUser(
        var username: String?,
        var email: String?,
        var password: String?
)