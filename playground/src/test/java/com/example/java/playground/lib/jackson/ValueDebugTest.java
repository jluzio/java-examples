package com.example.java.playground.lib.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
class ValueDebugTest {

  record Data(List<DataItem> items) {

  }

  record DataItem(String key, String value, String optionalValue) {

  }

  @Test
  void debug() throws JacksonException {
    ObjectMapper objectMapper = JsonMapper.builder()
        .disable(tools.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
        .build();

    var data = new Data(List.of(
        new DataItem("key-1", "value-1", "optional-1"),
        new DataItem("key-2", "value-2", null)
    ));

    log.info("{}", objectMapper.writeValueAsString(data));
  }

}
