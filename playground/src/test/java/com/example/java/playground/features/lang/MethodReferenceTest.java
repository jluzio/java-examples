package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

class MethodReferenceTest {

  @RequiredArgsConstructor
  static class SomeClass {

    private final String id;


    public String value1() {
      return id + "1";
    }

    public String value2() {
      return id + "2";
    }
  }

  @Test
  void instanceRef() {
    Function<SomeClass, String> methodRef1 = SomeClass::value1;
    assertThat(methodRef1.apply(new SomeClass("someId")))
        .isEqualTo("someId1");
  }

}
