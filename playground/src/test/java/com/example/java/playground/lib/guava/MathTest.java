package com.example.java.playground.lib.guava;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

class MathTest {

  @Test
  void longMath() {
    assertThat(LongMath.log2(128, RoundingMode.CEILING))
        .isEqualTo(7);
    assertThat(LongMath.log2(132, RoundingMode.CEILING))
        .isEqualTo(8);

    assertThat(LongMath.gcd(6, 8))
        .isEqualTo(2);
  }

  @Test
  void doubleMath() {
    assertThat(DoubleMath.log2(128))
        .isEqualTo(7.0);
    assertThat(DoubleMath.log2(132))
        .isGreaterThan(7.0);
  }

}
