package org.example.backend.model.response;

import java.util.Map;
import pl.net.testit.serum.commons.json.JsonEntity;

public class ErrorResBody  extends JsonEntity {

  public Map<String, String> errors;
}
