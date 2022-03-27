package com.example.java.playground.features.lang;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class VarKeywordTest {

  @Test
  void test() {
    var value = "123";

    log.info(value.substring(1));

    for (var v : List.of(1, 2, 3)) {
      log.info("v: {}", v);
    }
  }
}
