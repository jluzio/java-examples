package com.example.java.playground.lib.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Slf4jTest {

  @Test
  void test() {
    Throwable throwable = new RuntimeException("Some exception");
    log.debug("message: {}", "error has occurred", throwable);
  }

}
