package com.example.java.playground.lib.testdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

@Slf4j
class InstancioTest {

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class Todo {

    private String id;
    private String name;
    private String description;
    private LocalDate completedAt;
  }

  @Test
  void test() {
    Todo todo = Instancio.of(Todo.class).create();
    log.info("todo: {}", todo);
    assertThat(todo)
        .hasNoNullFieldsOrProperties();
  }

}
