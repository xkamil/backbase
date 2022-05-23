package org.example.backend.sampler

import org.example.backend.model.request.UserRegisterReqBody
import org.example.backend.model.request.UserRegisterReqBody.User

import static org.example.backend.extensions.DataGenerator.*

class UserRegisterSampler {

	static UserRegisterReqBody fullInput() {
		new UserRegisterReqBody().tap {
			user = new User().tap {
				username = generateUsername()
				email = generateEmail()
				password = generatePassword()
			}
		}
	}

}
