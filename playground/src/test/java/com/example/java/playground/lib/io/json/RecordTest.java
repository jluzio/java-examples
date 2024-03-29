package com.example.java.playground.lib.io.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RecordTest {

  record Data(String id, String value) {

  }

  @Test
    // Support for records from version 2.12.x
  void record() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    Data input = new Data("id1", "value1");
    String json = mapper.writeValueAsString(input);
    assertThat(json)
        .isEqualTo("{\"id\":\"id1\",\"value\":\"value1\"}");

    Data parsed = mapper.readValue(json, Data.class);
    assertThat(parsed)
        .isEqualTo(input);
  }
}
