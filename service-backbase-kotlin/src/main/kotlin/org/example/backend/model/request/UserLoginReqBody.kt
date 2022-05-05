package org.example.backend.model.request

import pl.net.testit.serum.commons.json.JsonEntity


class UserLoginReqBody(
        var user: User?
) : JsonEntity();

class User(
        var email: String?,
        var password: String?
)