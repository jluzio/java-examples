package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JacksonAutoConfiguration.class)
@Slf4j
class JsonPathTest {
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void basic_usage() throws JsonProcessingException {
    var json = """
        {"data": {"value": "someval"}}
        """;

    assertThat(JsonPath.<Object>read(json, "$.data"))
        .isNotNull()
        .satisfies(it -> log.debug("{}", it))
        .isInstanceOf(Map.class)
        .isEqualTo(Map.of("value", "someval"));

    var jsonPath = JsonPath.compile("$.data");
    assertThat(jsonPath.<Object>read(json))
        .isNotNull()
        .satisfies(it -> log.debug("{}", it))
        .isInstanceOf(Map.class)
        .isEqualTo(Map.of("value", "someval"));

    assertThat(objectMapper.writeValueAsString(jsonPath.read(json)))
        .isEqualTo("""
            {"value":"someval"}""");
  }

}
