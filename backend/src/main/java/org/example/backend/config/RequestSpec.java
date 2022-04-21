package org.example.backend.config;

import com.example.serum.api.filter.RequestLoggingFilter;
import com.example.serum.api.filter.ResponseLoggingFilter;
import com.example.serum.commons.json.JsonParser;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.params.CoreConnectionPNames;

public class RequestSpec {

  public static RestAssuredConfig baseRestAssuredConfig() {

    var httpClientConfig = HttpClientConfig.httpClientConfig()
        .dontReuseHttpClientInstance()
        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 30_000)
        .setParam(CoreConnectionPNames.SO_TIMEOUT, 30_000);

    var objectMapperConfig = ObjectMapperConfig.objectMapperConfig()
        .jackson2ObjectMapperFactory((aClass, s) -> JsonParser.getObjectMapper());

    return RestAssuredConfig.newConfig()
        .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation().allowAllHostnames())
        .httpClient(httpClientConfig)
        .objectMapperConfig(objectMapperConfig);
  }

  public static RequestSpecBuilder baseJsonRequestSpecBuilder() {
    return new RequestSpecBuilder()
        .addFilter(new RequestLoggingFilter())
        .addFilter(new ResponseLoggingFilter())
        .setConfig(baseRestAssuredConfig())
        .setContentType(ContentType.APPLICATION_JSON.toString());
  }

}
