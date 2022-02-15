package com.example.java.playground.lib.lombok;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

class AccessorTest {

  @Data
  @Accessors(fluent = true, chain = true)
  static class Person {

    private String firstName;
    private String lastName;
  }

  @Test
  void test() {
    var person = new Person()
        .firstName("fname")
        .lastName("lname");
    assertThat(person)
        .hasFieldOrPropertyWithValue("firstName", "fname")
        .hasFieldOrPropertyWithValue("lastName", "lname");
  }

}
