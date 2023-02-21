package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SealedClassTest {

  public abstract sealed class Person
      permits Employee, Manager {

    //...
  }

  public final class Employee extends Person {

  }

  public non-sealed class Manager extends Person {

  }

  public abstract class NonSealed {

  }
  public class OnlyClassOfNonSealed extends NonSealed {

  }

  @Test
  void test() {
    assertThat(describePerson(new Employee()))
        .isEqualTo("Employee");
    assertThat(describeNonSealed(new OnlyClassOfNonSealed()))
        .isEqualTo("OnlyClassOfNonSealed");
  }

  private String describePerson(Person person) {
    return switch (person) {
      case Employee e -> "Employee";
      case Manager e -> "Manager";
    };
  }

  private String describeNonSealed(NonSealed nonSealed) {
    return switch (nonSealed) {
      case OnlyClassOfNonSealed e -> "OnlyClassOfNonSealed";
      // required to add default
      default -> "Unknown";
    };
  }
}
