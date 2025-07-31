package com.example.java.playground.features.lang;

import module java.base;      // exports java.util, which has a public List interface
import module java.desktop;   // exports java.awt, which a public List class
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ModuleImportAmbiguousTest {

  @Test
  void test() {
    assertThat(Stream.class.getName())
        .isEqualTo(java.util.stream.Stream.class.getName());
    assertThat(Point.class.getName())
        .isEqualTo(java.awt.Point.class.getName());

    // note the imports section: additional import of java.util.List to fix ambiguous import
    assertThat(List.class.getName())
        .isEqualTo(java.util.List.class.getName());
  }

}
