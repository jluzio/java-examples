package com.example.java.playground.lib.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DebugValueTest {

  record Data(List<DataItem> items) {

  }

  record DataItem(String key, String value, String optionalValue) {

  }

  @Test
  void debug() throws JsonProcessingException {
    ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper()
        .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
        .registerModule(new com.fasterxml.jackson.datatype.jdk8.Jdk8Module())
        .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
        ;

    var data = new Data(List.of(
        new DataItem("key-1", "value-1", "optional-1"),
        new DataItem("key-2", "value-2", null)
    ));

    log.debug("{}", objectMapper.writeValueAsString(data));
  }

}
