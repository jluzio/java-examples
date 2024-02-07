package com.example.java.playground.lib.commons.text;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Test;

class StringSubstitutorTest {

  @Test
  void test() {
    var data = Map.of(
        "name1", "Foo",
        "name2", "Bar"
    );
    var stringSubstitutor = new StringSubstitutor(data);

    assertThat(
        stringSubstitutor.replace("Hello ${name1}! Hello ${name2}! Bye ${name1} and ${name2}!"))
        .isEqualTo("Hello Foo! Hello Bar! Bye Foo and Bar!");
  }
}
