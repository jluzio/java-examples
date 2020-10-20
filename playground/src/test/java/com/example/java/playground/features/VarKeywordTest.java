package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VarKeywordTest extends AbstractTest {

  @Test
  void test() {
    var value = "123";

    log.info(value.substring(1));

    for (var v : List.of(1, 2, 3)) {
      log.info("v: {}", v);
    }
  }
}
