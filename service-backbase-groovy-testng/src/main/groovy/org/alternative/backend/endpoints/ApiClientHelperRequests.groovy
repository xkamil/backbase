package org.alternative.backend.endpoints

import io.restassured.specification.RequestSpecification
import org.alternative.backend.ApiClient
import org.alternative.backend.endpoints.user.login.UserLoginRequestBody

import static org.alternative.backend.base.EnsureResponse.extract

class ApiClientHelperRequests {
    private final ApiClient apiClient
    private final RequestSpecification requestSpec

    ApiClientHelperRequests(ApiClient apiClient) {
        this.apiClient = apiClient
        this.requestSpec = apiClient.getRequestSpec()
    }

    ApiClient authenticate(String email, String password) {
        def reqBody = new UserLoginRequestBody(
                user: new UserLoginRequestBody.User(email: email, password: password)
        )
        def response = apiClient.user().login(reqBody)
        def token = extract(response) { response.parse().user.token }
        apiClient.setAuthToken("Token $token")
    }
}
