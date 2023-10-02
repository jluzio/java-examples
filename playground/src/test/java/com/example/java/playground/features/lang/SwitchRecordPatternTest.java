package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class SwitchRecordPatternTest {

  record Point(int x, int y) {

  }

  @Test
  void test() {
    Object switchVal = new Point(1, 2);
    var returnValue = print(switchVal);
    log.info("Result was: %s".formatted(returnValue));
    assertThat(returnValue).isEqualTo("o is a position: {1,2}");
  }

  public String print(Object o) {
    return switch (o) {
      case Point(int x, int y) -> "o is a position: {%d,%d}".formatted(x, y);
      case String s -> "o is a string: %s".formatted(s);
      default -> "o is something else: %s".formatted(o);
    };
  }
}
