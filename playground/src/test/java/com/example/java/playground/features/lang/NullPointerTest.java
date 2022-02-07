package com.example.java.playground.features.lang;

import com.example.java.playground.AbstractTest;
import lombok.Data;
import org.junit.jupiter.api.Test;

public class NullPointerTest extends AbstractTest {

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
