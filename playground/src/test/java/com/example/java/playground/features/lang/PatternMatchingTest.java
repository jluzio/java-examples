package com.example.java.playground.features.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class PatternMatchingTest {

  @Test
  void test_instance_of() {
    Object object = "   string-value   ";
    if (object instanceof String objAsStr) {
      log.info("object is string: {}", objAsStr.trim());
    } else {
      log.info("object is other type: {}", object);
    }
  }
}
