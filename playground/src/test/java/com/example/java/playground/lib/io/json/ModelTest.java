package com.example.java.playground.lib.io.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.java.playground.AbstractTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class ModelTest extends AbstractTest {

  @Test
  void simple_mapping() throws JsonProcessingException {
    var dataItem = DataItem.builder()
        .id("id-1")
        .description("desc")
        .numberValue(123)
        .localDateTime(LocalDateTime.now().plusHours(3))
        .references(List.of("ref-1", "ref-2"))
        .build();

    var objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    var dataAsString = objectMapper.writeValueAsString(dataItem);

    log.info("data{}{}", System.lineSeparator(), dataAsString);
  }

  @Test
  void empty() throws JsonProcessingException {
    var objectMapper = new ObjectMapper();
    assertThatThrownBy(() -> objectMapper.readValue((String)null, DataItem.class))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> objectMapper.readValue("", DataItem.class))
        .isInstanceOf(MismatchedInputException.class);
    assertThat(objectMapper.readValue("null", DataItem.class))
        .isNull();
  }
}
