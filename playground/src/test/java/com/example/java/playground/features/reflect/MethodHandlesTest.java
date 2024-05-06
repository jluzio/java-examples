package com.example.java.playground.features.reflect;

import java.lang.invoke.MethodHandles;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MethodHandlesTest {

  private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Test
  void test() {
    var lookup = MethodHandles.lookup();
    log.info("currentClass: {}", lookup.lookupClass().getName());
  }

}
