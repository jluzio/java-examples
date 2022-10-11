package com.example.java.playground.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SystemConfigHelperTest {

  @Test
  void convertToEnvironmentConfigKey() {
    assertThat(SystemConfigHelper.convertToEnvironmentConfigKey("key1"))
        .isEqualTo("KEY1");
    assertThat(SystemConfigHelper.convertToEnvironmentConfigKey("someKey1"))
        .isEqualTo("SOME_KEY1");
    assertThat(SystemConfigHelper.convertToEnvironmentConfigKey("sample.key1"))
        .isEqualTo("SAMPLE_KEY1");
    assertThat(SystemConfigHelper.convertToEnvironmentConfigKey("sample.someKey1"))
        .isEqualTo("SAMPLE_SOME_KEY1");
    assertThat(SystemConfigHelper.convertToEnvironmentConfigKey("sample.some-key1"))
        .isEqualTo("SAMPLE_SOME_KEY1");
  }
}