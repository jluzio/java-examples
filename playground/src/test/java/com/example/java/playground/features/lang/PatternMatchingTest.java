package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class PatternMatchingTest {

  @Test
  void test_instance_of() {
    Function<Object, String> testObject = object -> {
      if (object instanceof String objAsStr) {
        return "string: %s".formatted(objAsStr.length());
      } else {
        return "other type";
      }
    };
    assertThat(testObject.apply("   string-value   "))
        .isEqualTo("string: 18");
  }

  @Test
  void test_instance_of_with_conditions() {
    Function<Object, String> testObject = object -> {
      if (object instanceof String objAsStr && objAsStr.length() > 10) {
        return "long-string: %s".formatted(objAsStr.length());
      } else if (object instanceof String objAsStr) {
        return "string: %s".formatted(objAsStr.length());
      } else {
        return "other type";
      }
    };
    assertThat(testObject.apply("   string-value   "))
        .isEqualTo("long-string: 18");
  }

  @Test
  void switch_matching() {
    Function<Object, String> testType = o ->
        switch (o) {
          case String v -> "string";
          case Number v -> "number";
          default -> "unknown";
        };
    assertThat(testType.apply("test string"))
        .isEqualTo("string");
    assertThat(testType.apply(1))
        .isEqualTo("number");
  }

  @Test
  void switch_matching_using_with() {
    Function<Object, String> testType = o ->
        switch (o) {
          case String v
              when v.length() >= 10 -> "long-string";
          case String v -> "string";
          case Number v -> "number";
          default -> "unknown";
        };
    assertThat(testType.apply("testing string"))
        .isEqualTo("long-string");
    assertThat(testType.apply("test"))
        .isEqualTo("string");
    assertThat(testType.apply(1))
        .isEqualTo("number");
  }
}
