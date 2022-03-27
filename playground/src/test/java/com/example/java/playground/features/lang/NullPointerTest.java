package com.example.java.playground.features.lang;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class NullPointerTest {

  @Data
  class Foo {

    private Bar bar;
  }

  @Data
  class Bar {

    private String value;
  }

  @Test
  void test() {
    Foo foo = null;

    try {
      log.info("foo.bar.value: {}", foo.getBar().getValue());
    } catch (Exception e) {
      log.info("NPE[foo]: {}", e.getMessage());
      e.printStackTrace();
    }

    foo = new Foo();

    try {
      log.info("foo.bar.value: {}", foo.getBar().getValue());
    } catch (Exception e) {
      log.info("NPE[foo.bar]: {}", e.getMessage());
      e.printStackTrace();
    }
  }
}
