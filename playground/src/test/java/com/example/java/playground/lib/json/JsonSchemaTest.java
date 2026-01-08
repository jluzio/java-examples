package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.Todo;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
class JsonSchemaTest {

  private ObjectMapper mapper = JsonMapper.builder().build();

  @Test
  void testGeneratedClasses() throws JacksonException {
    var todo = new Todo()
        .withId(1)
        .withDate(LocalDate.of(2345, 1, 2));

    log.info("todo: {}", mapper.writeValueAsString(todo));
    assertThat(mapper.writeValueAsString(todo))
        .isEqualTo("{\"id\":1,\"date\":\"2345-01-02\"}");
  }

}
