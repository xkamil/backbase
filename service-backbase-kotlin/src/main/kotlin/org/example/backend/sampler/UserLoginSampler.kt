package org.example.backend.sampler

import org.example.backend.model.request.User
import org.example.backend.model.request.UserLoginReqBody

object UserLoginSampler {

    fun fullInput(email: String?, password: String?): UserLoginReqBody? {
        return UserLoginReqBody(
                user = User(
                        email,
                        password
                )
        );
    }
}