package com.example.java.playground.features.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoundChecksPatternMatchingTest {

  @Test
  void bound_checks() {
    int number = 0;
    if (number instanceof byte b) {
      IO.println("value '%s' can be represented as byte (%s)".formatted(number, b));
    } else {
      IO.println("value '%s' can't be represented as byte".formatted(number));
      Assertions.fail();
    }

    number = 128;
    if (number instanceof byte b) {
      IO.println("value '%s' can be represented as byte (%s)".formatted(number, b));
      Assertions.fail();
    } else {
      IO.println("value '%s' can't be represented as byte".formatted(number));
    }
  }

  @Test
  void precision_checks() {
    int number = 16_777_217;
    IO.println(number);
    if (number instanceof float f) { // can't be converted due to loss of precision
      IO.println("value '%s' can be represented as float (%s)".formatted(number, f));
      Assertions.fail();
    } else {
      IO.println("value '%s' can't be represented as float".formatted(number));
    }
  }

}
