package com.example.java.playground.features.lang;

import module java.base;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ModuleImportTest {

  @Test
  void test() {
    // note the imports section
    assertThat(Stream.class.getName())
        .isEqualTo(java.util.stream.Stream.class.getName());
    assertThat(Stream.of(1, 2, 3).toList())
        .isEqualTo(List.of(1, 2, 3));
  }

}
