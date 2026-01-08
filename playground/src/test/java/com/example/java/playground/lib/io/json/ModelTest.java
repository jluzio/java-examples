package com.example.java.playground.lib.io.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.MismatchedInputException;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
class ModelTest {

  @Test
  void simple_mapping() throws JacksonException {
    var dataItem = DataItem.builder()
        .id("id-1")
        .description("desc")
        .numberValue(123)
        .localDateTime(LocalDateTime.now().plusHours(3))
        .references(List.of("ref-1", "ref-2"))
        .build();

    var objectMapper = JsonMapper.builder().build();
    var dataAsString = objectMapper.writeValueAsString(dataItem);

    log.info("data{}{}", System.lineSeparator(), dataAsString);
  }

  @Test
  void empty() throws JacksonException {
    var objectMapper = new ObjectMapper();
    assertThatThrownBy(() -> objectMapper.readValue((String) null, DataItem.class))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> objectMapper.readValue("", DataItem.class))
        .isInstanceOf(MismatchedInputException.class);
    assertThat(objectMapper.readValue("null", DataItem.class))
        .isNull();
  }
}
