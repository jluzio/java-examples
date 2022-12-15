package com.example.java.playground.lib.guava;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;

class CaseFormatTest {

  @Test
  void convert() {
    assertThat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, getClass().getSimpleName()))
        .isEqualTo("case-format-test");

    var envVarFormatConverter = CaseFormat.LOWER_CAMEL
        .converterTo(CaseFormat.UPPER_UNDERSCORE)
        .andThen(v -> v.replace('.', '_'));
    assertThat(envVarFormatConverter.apply("app.service.enableFeature"))
        .isEqualTo("APP_SERVICE_ENABLE_FEATURE");
  }
}
