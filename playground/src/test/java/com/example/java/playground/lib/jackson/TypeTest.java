package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TypeTest {

  record Todo(String id, String title, boolean done) {

  }

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  void using_JavaType_by_TypeFactory() throws JsonProcessingException {
    var todo = new Todo("id1", "title1", true);
    var json = mapper.writeValueAsString(todo);
    Map<String, Object> todoAsMap = mapper.readValue(
        json,
        TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class));

    assertThat(todoAsMap)
        .containsExactlyInAnyOrderEntriesOf(Map.of(
            "id", "id1",
            "title", "title1",
            "done", true
        ));
  }

  @Test
  void using_TypeReference() throws JsonProcessingException {
    var todo = new Todo("id1", "title1", true);
    var json = mapper.writeValueAsString(todo);
    Map<String, Object> todoAsMap = mapper.readValue(
        json,
        new TypeReference<HashMap<String, Object>>() {
        });

    assertThat(todoAsMap)
        .containsExactlyInAnyOrderEntriesOf(Map.of(
            "id", "id1",
            "title", "title1",
            "done", true
        ));
  }

  @Test
  void using_Type() throws JsonProcessingException {
    var todo = new Todo("id1", "title1", true);
    var json = mapper.writeValueAsString(todo);
    Todo todoAsRecord = mapper.readValue(
        json,
        Todo.class);

    assertThat(todoAsRecord)
        .isEqualTo(todo);
  }

}
