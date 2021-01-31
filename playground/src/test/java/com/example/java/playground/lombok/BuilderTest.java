package com.example.java.playground.lombok;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class BuilderTest {

  @Data
  @SuperBuilder
  public static class Parent {

    private String parent1;
  }

  @Data
  @ToString(callSuper = true)
  @SuperBuilder
  public static class Child extends Parent {

    private String child1;
  }

  @Test
  void superBuilder() {
    Child child = Child.builder()
        .parent1("val1")
        .child1("val2")
        .build();
    log.info("child: {}", child);
  }

}
