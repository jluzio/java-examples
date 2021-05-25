package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.Todo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JsonSchemaTest {

  private ObjectMapper mapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  @Test
  void testGeneratedClasses() throws JsonProcessingException {
    var todo = new Todo()
        .withId(1)
        .withDate(LocalDate.of(2345, 1, 2));

    log.info("todo: {}", mapper.writeValueAsString(todo));
    assertThat(mapper.writeValueAsString(todo))
        .isEqualTo("{\"id\":1,\"date\":\"2345-01-02\"}");
  }

}
