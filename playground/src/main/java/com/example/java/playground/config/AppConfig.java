package com.example.java.playground.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class AppConfig {

  @Bean
  public ObjectMapper getObjectMapper() {
    return JsonMapper.builder()
        // disabled by default in 3.x
        //.disable(tools.jackson.databind.cfg.DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build();
  }
}
