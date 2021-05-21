package com.example.java.playground.lib.log;

import static com.example.java.playground.lib.log.ToStringSupplier.asString;

import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ToStringSupplierTest {

  @Test
  void lazyLog() {
    Supplier<Object> expensiveCallSupplier = () -> "expensive call result";
    log.debug("lazyLog {}", asString(expensiveCallSupplier));
  }

}