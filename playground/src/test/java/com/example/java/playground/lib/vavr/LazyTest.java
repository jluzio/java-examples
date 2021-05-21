package com.example.java.playground.lib.vavr;

import io.vavr.Lazy;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

class LazyTest {

  @Test
  void test() {
    Supplier<String> expensiveCall = () -> "expensive call result";
    Lazy.of(expensiveCall)
        .transform(Lazy::get);
  }

}
