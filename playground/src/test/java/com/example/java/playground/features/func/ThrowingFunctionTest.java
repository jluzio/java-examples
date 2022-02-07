package com.example.java.playground.features.func;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ThrowingFunctionTest {

  @Test
  void apply() {
    assertThatThrownBy(
        () -> Stream.of("1", "2", "")
            .map(ThrowingFunction.unchecked(this::throwingFunction))
            .collect(Collectors.toList())
    )
        .isInstanceOf(RuntimeException.class)
        .getCause()
        .isInstanceOf(MismatchedInputException.class);
  }

  private Integer throwingFunction(String json) throws IOException {
    Integer value = new ObjectMapper().readValue(json, Integer.class);
    log.info("value: {}", value);
    return value;
  }
}