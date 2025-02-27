package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StringTemplateTest {

  @Test
  void test() {
    var name = "world";
    assertThat(STR."Greetings \{ name }!")
        .isEqualTo("Greetings world!");
    assertThat(STR."Greetings \{ name } (\{ name.length() })!")
        .isEqualTo("Greetings world (5)!");
  }

}
