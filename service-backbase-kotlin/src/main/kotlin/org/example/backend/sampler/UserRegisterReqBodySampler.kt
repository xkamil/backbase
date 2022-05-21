package org.example.backend.sampler

import org.example.backend.model.request.UserRegisterReqBody
import org.example.backend.model.request.UserRegisterReqBodyUser
import java.util.*

object UserRegisterReqBodySampler {

    fun fullInput(modify: (UserRegisterReqBody) -> Unit = {}): UserRegisterReqBody {
        val reqBody = UserRegisterReqBody(
                user = UserRegisterReqBodyUser(
                        username = UUID.randomUUID().toString().substring(25),
                        email = "${UUID.randomUUID()} @example.com",
                        password = UUID.randomUUID().toString().substring(10).replace("-", ""),
                )
        )

        modify.invoke(reqBody)
        return reqBody
    }
}