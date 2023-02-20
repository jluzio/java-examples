package com.example.java.playground.lib.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Slf4jTest {

  @Test
  void exception() {
    Throwable throwable = new RuntimeException("Some exception");
    log.info("message: {}", "error has occurred", throwable);
  }

  @Test
  void levels() {
    log.error("error");
    log.warn("warn");
    log.info("info");
    log.info("debug");
    log.trace("trace");
  }

}
