package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SealedInterfaceTest {

  public sealed interface Person
      permits Employee, Manager {

    //...
  }

  public final class Employee implements Person {

  }

  public non-sealed class Manager implements Person {

  }

  @Test
  void test() {
    assertThat(describePerson(new Employee()))
        .isEqualTo("Employee");
  }

  private String describePerson(Person person) {
    return switch (person) {
      case Employee e -> "Employee";
      case Manager e -> "Manager";
    };
  }

}
