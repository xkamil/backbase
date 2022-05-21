package org.example.backend.config

import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.HttpClientConfig
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.config.SSLConfig
import org.apache.http.entity.ContentType
import org.apache.http.params.CoreConnectionPNames
import pl.net.testit.serum.api.filter.RequestLoggingFilter
import pl.net.testit.serum.api.filter.ResponseLoggingFilter
import pl.net.testit.serum.commons.json.JsonParser
import java.lang.reflect.Type

object BackbaseRequestSpec {
  fun baseRestAssuredConfig(): RestAssuredConfig {
    val httpClientConfig = HttpClientConfig.httpClientConfig()
      .dontReuseHttpClientInstance()
      .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000)
      .setParam(CoreConnectionPNames.SO_TIMEOUT, 30000)
    val objectMapperConfig = ObjectMapperConfig.objectMapperConfig()
      .jackson2ObjectMapperFactory { aClass: Type?, s: String? -> JsonParser.getObjectMapper() }
    return RestAssuredConfig.newConfig()
      .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation().allowAllHostnames())
      .httpClient(httpClientConfig)
      .objectMapperConfig(objectMapperConfig)
  }

  fun baseJsonRequestSpecBuilder(): RequestSpecBuilder {
    return RequestSpecBuilder()
      .addFilter(RequestLoggingFilter())
      .addFilter(ResponseLoggingFilter())
      .setConfig(baseRestAssuredConfig())
      .setContentType(ContentType.APPLICATION_JSON.toString())
  }
}