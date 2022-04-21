package org.example.backend.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Configuration {

  public static final Config conf = ConfigFactory.load();

  public static final String ENV = conf.getString("environment");
}
