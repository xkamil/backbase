package org.example.backend.sampler

import org.example.backend.model.request.UserRegisterReqBody
import java.util.*
import java.util.function.Consumer

object UserRegisterSampler {
    @SafeVarargs
    fun fullInput(vararg modify: Consumer<UserRegisterReqBody.User?>?): UserRegisterReqBody? {
        val user = UserRegisterReqBody.User()
                .username(UUID.randomUUID().toString().substring(25))
                .email(UUID.randomUUID().toString() + "@example.com")
                .password(UUID.randomUUID().toString().substring(10).replace("-", ""))
        Arrays.stream(modify).forEach(Consumer { m: Consumer<UserRegisterReqBody.User?> -> m.accept(user) })
        return UserRegisterReqBody().user(user)
    }
}