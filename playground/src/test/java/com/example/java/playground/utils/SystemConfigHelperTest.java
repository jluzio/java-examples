package com.example.java.playground.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SystemConfigHelperTest {

  @Test
  void toEnvironmentConfigKey() {
    assertThat(SystemConfigHelper.toEnvironmentConfigKey("key1"))
        .isEqualTo("KEY1");
    assertThat(SystemConfigHelper.toEnvironmentConfigKey("someKey1"))
        .isEqualTo("SOME_KEY1");
    assertThat(SystemConfigHelper.toEnvironmentConfigKey("sample.key1"))
        .isEqualTo("SAMPLE_KEY1");
    assertThat(SystemConfigHelper.toEnvironmentConfigKey("sample.someKey1"))
        .isEqualTo("SAMPLE_SOME_KEY1");
    assertThat(SystemConfigHelper.toEnvironmentConfigKey("sample.some-key1"))
        .isEqualTo("SAMPLE_SOME_KEY1");
  }
}