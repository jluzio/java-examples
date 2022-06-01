package com.example.java.playground.lib.guava;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;

class CaseFormatTest {

  @Test
  void convert() {
    assertThat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, getClass().getSimpleName()))
        .isEqualTo("case-format-test");
  }
}
