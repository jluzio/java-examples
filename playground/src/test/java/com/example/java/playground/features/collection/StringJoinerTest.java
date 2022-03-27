package com.example.java.playground.features.collection;

import java.util.List;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class StringJoinerTest {

  @Test
  void test() {
    var joiner = new StringJoiner(", ", "{", "}");
    List.of("1", "2", "3").forEach(joiner::add);
    log.info(joiner.toString());
  }
}
