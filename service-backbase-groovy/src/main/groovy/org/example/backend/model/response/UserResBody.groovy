package org.example.backend.model.response

import pl.net.testit.serum.commons.json.JsonEntity

class UserResBody extends JsonEntity {

	User user

	static class User {
		String email
		String token
		String username
		String bio
		String image
	}

}
