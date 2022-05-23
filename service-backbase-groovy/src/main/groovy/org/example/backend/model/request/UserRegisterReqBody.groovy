package org.example.backend.model.request

import pl.net.testit.serum.commons.json.JsonEntity

class UserRegisterReqBody extends JsonEntity {

	User user

	static class User {
		String username
		String email
		String password
	}

}
