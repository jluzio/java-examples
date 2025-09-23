package com.example.java.playground.lib.jspecify.spring;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Consumer;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;

class ContractTest {

  @Test
  void test_assertState() {
    Consumer<@Nullable String> processValue = value -> {
      // Spring 7 / Spring Boot 4 will add @Contract to these methods
      // then it will be able to configure compile time checks for the custom Contract.
      // - Gradle example: <code>option("NullAway:CustomContractAnnotations", "org.springframework.lang.Contract")</code>
      Assert.state(value != null, "value is null");
      IO.println(value.length());
    };

    var v = nullableValue();
    assertThatThrownBy(() -> processValue.accept(v))
        .isInstanceOf(IllegalStateException.class);
  }

  @Nullable
  String nullableValue() {
    return null;
  }

}
