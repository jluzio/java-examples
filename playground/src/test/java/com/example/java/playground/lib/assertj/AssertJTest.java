package com.example.java.playground.lib.assertj;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
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

  @Test
  void exceptions() {
    assertThatCode(() -> {
    })
        .doesNotThrowAnyException();

    assertThatNoException()
        .isThrownBy(() -> {
        });

    assertThatCode(() -> {
      throw new IllegalArgumentException("Bad data");
    })
        .isInstanceOf(IllegalArgumentException.class)
        .satisfies(e -> log.info("Thrown", e));

    assertThatThrownBy(() -> {
      throw new IllegalArgumentException("Bad data");
    })
        .isInstanceOf(IllegalArgumentException.class)
        .satisfies(e -> log.info("Thrown", e));
  }

}
