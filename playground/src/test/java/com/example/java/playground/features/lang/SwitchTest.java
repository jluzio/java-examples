package com.example.java.playground.features.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class SwitchTest {

  @Test
  void test() {
    var switchVal = "test";
    var returnValue = switch (switchVal) {
      case "x", "y" -> 1;
      case "z" -> 2;
      case "test" -> {
        log.info("entering block");
        yield 99;
      }
      default -> 0;
    };
    log.info("Result was: %s".formatted(returnValue));
  }
}
