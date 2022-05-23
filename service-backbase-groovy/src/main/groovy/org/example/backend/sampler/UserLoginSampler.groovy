package org.example.backend.sampler

import org.example.backend.model.request.UserLoginReqBody
import org.example.backend.model.request.UserLoginReqBody.User

class UserLoginSampler {

	static UserLoginReqBody fullInput(String email, String password) {
		new UserLoginReqBody().tap {
			user = new User().tap {
				it.email = email
				it.password = password
			}
		}
	}
}
