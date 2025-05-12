package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class FlexibleConstructorsTest {

  static class PositiveBigInteger extends BigInteger {

    public PositiveBigInteger(long value) {
      if (value <= 0) throw new IllegalArgumentException("negative value");
      super(String.valueOf(value));
    }

  }
  @Test
  void test() {
    assertThatThrownBy(() -> new PositiveBigInteger(-123))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("negative value");

    assertThat(new PositiveBigInteger(123))
        .isNotNegative();
  }

}
