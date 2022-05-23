package org.example.backend.extensions

import groovy.json.JsonSlurper
import pl.net.testit.serum.api.request.RequestQueryParams

import static groovy.json.JsonOutput.toJson

class GroovyRequestQueryParams extends RequestQueryParams {

  private final static JsonSlurper JSON_SLURPER = new JsonSlurper()

  @Override
  Map<String, String> asMap() {
    // parse object to json string and then back to map
    // to get rid of internal groovy properties
    // then remove all null-value entries
    Map queryParams = (JSON_SLURPER.parseText(toJson(this)) as Map)
        .findAll { it.value != null } as Map
    this.getClass().getDeclaredFields().each {
      def annotation = it.getAnnotation(QueryParamName)
      def fieldName = it.name
      // for annotated replace field-name with annotation value
      if (annotation && fieldName in queryParams.keySet()) {
        def paramName = annotation.value()
        queryParams[paramName] = queryParams[fieldName]
        queryParams.remove(fieldName)
      }
    }
    queryParams
  }
}
