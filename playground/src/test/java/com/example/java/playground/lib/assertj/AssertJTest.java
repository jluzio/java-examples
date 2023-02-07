package com.example.java.playground.lib.assertj;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssertJTest {

  record Todo(String id, String task, LocalDate startDate, LocalDate endDate) {

  }

  @Test
  void property_validations() {
    var todos = List.of(
        new Todo("1", "Learn", LocalDate.now(), null),
        new Todo("2", "Do example", LocalDate.now(), LocalDate.now())
    );
    assertThat(todos)
        .hasSize(2)
        .allSatisfy(todo -> assertThat(todo)
            .hasFieldOrProperty("id")
            .hasFieldOrProperty("endDate")
            .satisfies(v -> assertThat(v.startDate).isNotNull())
        );
    assertThat(todos.get(0))
        .satisfies(v -> assertThat(v.endDate()).isNull());
  }

}
