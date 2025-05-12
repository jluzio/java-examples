package com.example.java.playground.features.lang;

import module java.base;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ModuleImportTest {

  @Test
  void test() {
    // note the imports section
    assertThat(Stream.class)
        .isInstanceOf(java.util.stream.Stream.class);
  }

}
