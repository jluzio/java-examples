package com.example.java.playground.features.collection;

import com.example.java.playground.AbstractTest;
import java.util.List;
import java.util.StringJoiner;
import org.junit.jupiter.api.Test;

class StringJoinerTest extends AbstractTest {

  @Test
  void test() {
    var joiner = new StringJoiner(", ", "{", "}");
    List.of("1", "2", "3").forEach(joiner::add);
    log.info(joiner.toString());
  }
}
